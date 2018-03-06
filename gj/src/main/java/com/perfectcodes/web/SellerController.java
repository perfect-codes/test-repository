package com.perfectcodes.web;

import com.perfectcodes.common.BankCodeEnum;
import com.perfectcodes.common.CommonResp;
import com.perfectcodes.common.GeneralException;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.component.PropConfig;
import com.perfectcodes.domain.Banner;
import com.perfectcodes.domain.Seller;
import com.perfectcodes.service.BannerService;
import com.perfectcodes.service.SellerService;
import com.perfectcodes.util.WechatSignUtil;
import com.perfectcodes.web.vo.SellerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SellerController extends BaseController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private PropConfig propConfig;

    @RequestMapping(value = "/bankSpread")
    public String pageBankSpread(HttpServletRequest request, HttpServletResponse response, HttpSession session,Model model) throws Exception {
        String bankCode = request.getParameter("bankCode");
        String openid = (String) session.getAttribute("openid");
//        Seller seller = sellerService.findByOpenid(openid);
//        if (seller == null){//不是代理
//            //TODO 暂不处理
//        }
        String sellerLink = propConfig.getWebDomain() + "/wechat/" + getBankLink(bankCode);
        if (!StringUtils.isEmpty(openid)){
            sellerLink = sellerLink + "?seller="+openid;
        }
        model.addAttribute("sellerLink",sellerLink);
        //广告图
        Banner banner = new Banner();
        banner.setType(4);
        banner.setStatus(StatusEnum.NORMAL.getVal());
        try {
            List<Banner> banners = bannerService.findAll(banner);
            banner = banners.get(0);
            model.addAttribute("banner",banner);//默认取该类型第一张
        } catch (Exception e) {
            logger.error("推广页banner加载错误",e);
        }
        //微信js-sdk
        String url = propConfig.getWebDomain() + request.getRequestURI()+"?"+ request.getQueryString();
        Map<String, String> ret = WechatSignUtil.sign(PropConfig.getJsapiTicket(), url);
        model.addAllAttributes(ret);
        model.addAttribute("appId",propConfig.getAppid());
        model.addAttribute("imgUrl",propConfig.getWebDomain()+"/wechat/"+banner.getImgUrl());
        return "bankspread";
    }

    @ResponseBody
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public CommonResp regist(@RequestBody @Valid SellerVo sellerVo, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return failureResp().setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        Seller seller = new Seller();
        BeanUtils.copyProperties(sellerVo,seller);
        Date curDate = new Date();
        seller.setCreateDate(curDate);
        seller.setUpdateDate(curDate);
        seller.setStatus(StatusEnum.OFFLINE.getVal());
        try {
            sellerService.addBean(seller);
            return successResp();
        } catch (GeneralException e){
            return failureResp().setMessage(e.getMessage());
        }catch (Exception e) {
            logger.error("代理注册错误",e);
        }
        return failureResp();
    }

    /**
     * @author xpf
     * @description 获取银行页面地址
     * @date 2018/3/4 19:13
     */
    private static String getBankLink(String bankCode){
        for (BankCodeEnum bankCodeEnum:BankCodeEnum.values()){
            if (bankCodeEnum.getCode().equals(bankCode)){
                try {
                    return URLEncoder.encode(bankCodeEnum.toString(),"utf-8")+".html";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
