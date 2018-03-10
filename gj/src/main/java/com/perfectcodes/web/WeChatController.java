package com.perfectcodes.web;

import com.perfectcodes.common.*;
import com.perfectcodes.component.PropConfig;
import com.perfectcodes.component.SchedueConfig;
import com.perfectcodes.domain.Bank;
import com.perfectcodes.domain.Banner;
import com.perfectcodes.domain.Seller;
import com.perfectcodes.domain.User;
import com.perfectcodes.service.BankService;
import com.perfectcodes.service.BannerService;
import com.perfectcodes.service.SellerService;
import com.perfectcodes.service.UserService;
import com.perfectcodes.util.GJHttpUtil;
import com.perfectcodes.util.GJUtil;
import com.perfectcodes.util.WechatSignUtil;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WeChatController extends BaseController {

    @Autowired
    private SchedueConfig schedueConfig;
    @Autowired
    private PropConfig propConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BankService bankService;
    @Autowired
    private SellerService sellerService;

    @ResponseBody
    @RequestMapping(value = "/refresh_token", method = {RequestMethod.GET})
    public CommonResp test(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        schedueConfig.getToken();
        logger.info("手动执行刷新access_token成功");
        return successResp();
    }

    /**
     * @param name=bank|bankUser|bankSeller
     * @return
     * @throws IOException
     * @author xpf
     * @description 请求用户授权（跳转页面）
     * @date 2018/2/28 10:09
     */
    @RequestMapping(value = "/view/{name}", method = {RequestMethod.GET})
    public String index(@PathVariable String name, @RequestParam(required = false) Long seller) throws IOException {
        String state = "";
        if (name.equals("bankSelf")){
            state = name.toLowerCase();
        }else if (name.equals("bankSeller")) {
            state = name.toLowerCase() + "," + seller;
        }else{
            state = "bank";
        }
        String url = String.format("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s/wechat/code2token&response_type=code&scope=snsapi_userinfo&state=%s", propConfig.getAppid(), propConfig.getWebDomain(), state);
        logger.debug("当前access_token为：[{}]", PropConfig.getAccessToken());
        logger.debug("请求重定向地址为：[{}]", url);
        return url;
    }

    /**
     * @author xpf
     * @description 用户授权后回调(获取用户auth_token)
     * @date 2018/2/28 10:26
     */
    @RequestMapping(value = "/code2token", method = {RequestMethod.GET})
    public String code2Token(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws IOException {
        String code = request.getParameter("code");//code参数
        String state = request.getParameter("state");//state参数
        logger.debug(code + "," + state);
        HttpGet httpGetToken = new HttpGet(String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", propConfig.getAppid(), propConfig.getSecret(), code));
        HashMap<String, Object> result = GJHttpUtil.get(httpGetToken);
        String token = (String) result.get("access_token");
        String openid = (String) result.get("openid");
        //此处需要调用微信API获取用户头像等信息
        HttpGet httpGetUserinfo = new HttpGet(String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", token, openid));
        HashMap<String, Object> usermap = GJHttpUtil.get(httpGetUserinfo);
        logger.debug(usermap.toString());
        String scene = "bank";
        if (state != null && state.startsWith("bankseller")) {
            String[] params = state.split(",");
            scene = params[0];
            String leader = params[1];
            model.addAttribute("leader", leader);
            model.addAttribute("openid", openid);
            Banner banner = new Banner();
            banner.setType(3);
            banner.setStatus(StatusEnum.NORMAL.getVal());
            try {
                List<Banner> banners = bannerService.findAll(banner);
                model.addAttribute("banner", banners.get(0));//默认取该类型第一张
            } catch (Exception e) {
                logger.error("代理注册页banner加载错误", e);
            }
        } else if (state != null && state.startsWith("bankself")) {
            scene = "bankself";
            model.addAttribute("nickname",(String)usermap.get("nickname"));
            model.addAttribute("headimgurl",(String)usermap.get("headimgurl"));
            try {
                Seller seller = sellerService.findByOpenid(openid);
                if (seller != null) {
//                    logger.info("{}该seller用户不存在",openid);
//                    throw new GeneralException(ErrorCodeEnum.ERROR_NOT_EXIST);
                    model.addAttribute("seller", seller);
                    if (seller.getLeader() == null) {
//                        throw new GeneralException(ErrorCodeEnum.ERROR_ROLE);
                        //二级销售
                        List<Seller> followers = sellerService.findByLeader(seller.getId());
                        followers.forEach(follower -> {
//                            follower.setTelephone(GJUtil.hidePartPhone(follower.getTelephone()));
                            follower.setIdcard(GJUtil.hidePartIDCard(follower.getIdcard()));
                        });
                        model.addAttribute("followers", followers);
                    }
                    //业绩
                    List<User> users = userService.findBySeller(seller.getOpenid());
                    users.forEach(user -> {
                        user.setTelephone(GJUtil.hidePartPhone(user.getTelephone()));
                        user.setIdcard(GJUtil.hidePartIDCard(user.getIdcard()));
                    });
                    model.addAttribute("users", users);
                    //邀请处理
                    String sellerLink = propConfig.getWebDomain() + "/wechat/view/bankSeller";
                    if (!StringUtils.isEmpty(openid)){
                        sellerLink = sellerLink + "?seller="+seller.getId();
                    }
                    model.addAttribute("sellerLink",sellerLink);
                    //广告图
//                    Banner banner = new Banner();
//                    banner.setType(4);
//                    banner.setStatus(StatusEnum.NORMAL.getVal());
//                    try {
//                        List<Banner> banners = bannerService.findAll(banner);
//                        banner = banners.get(0);
//                        model.addAttribute("banner",banner);//默认取该类型第一张
//                    } catch (Exception e) {
//                        logger.error("推广页banner加载错误",e);
//                    }
                    //微信js-sdk
                    String url = propConfig.getWebDomain() + request.getRequestURI()+"?"+ request.getQueryString();
                    Map<String, String> ret = WechatSignUtil.sign(PropConfig.getJsapiTicket(), url);
                    model.addAllAttributes(ret);
                    model.addAttribute("appId",propConfig.getAppid());
//                    model.addAttribute("imgUrl",propConfig.getWebDomain()+"/wechat/"+banner.getImgUrl());
                    model.addAttribute("imgUrl",(String)usermap.get("headimgurl"));
                }
            } catch (Exception e) {
                logger.error("查询个人信息错误", e);
            }
        } else {//bank.html页面组装内容
            Banner banner = new Banner();
            banner.setType(1);
            banner.setStatus(StatusEnum.NORMAL.getVal());
            List<Banner> banners = null;
            try {
                banners = bannerService.findAll(banner);
            } catch (Exception e) {
                logger.error("办卡页banner列表查询错误", e);
            }
            model.addAttribute("banners", banners);
            //Bank
            Bank bank = new Bank();
            bank.setStatus(StatusEnum.NORMAL.getVal());
            List<Bank> banks = null;
            try {
                banks = bankService.findAll(bank);
            } catch (Exception e) {
                logger.error("办卡页银行列表查询错误", e);
            }
            model.addAttribute("banks", banks);
            //保存session
            session.setAttribute("openid", openid);
        }
        return scene;
    }

}
