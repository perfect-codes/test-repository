package com.perfectcodes.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.perfectcodes.common.CommonResp;
import com.perfectcodes.common.GeneralException;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.component.PropConfig;
import com.perfectcodes.component.SchedueConfig;
import com.perfectcodes.domain.Bank;
import com.perfectcodes.domain.Banner;
import com.perfectcodes.domain.User;
import com.perfectcodes.service.BankService;
import com.perfectcodes.service.BannerService;
import com.perfectcodes.service.UserService;
import com.perfectcodes.util.GJHttpUtil;
import com.perfectcodes.util.GJJsonUtil;
import com.perfectcodes.web.vo.UserVo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PublicController extends BaseController{

    @Autowired
    private DefaultKaptcha defaultKaptcha;
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
//
//    @RequestMapping(value = "/random.jpg",method = {RequestMethod.GET})
//    public void randomCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
//        String randomCode = defaultKaptcha.createText();
//        BufferedImage bufferedImage = defaultKaptcha.createImage(randomCode);
//        session.setAttribute(defaultKaptcha.getConfig().getSessionKey(),randomCode);
//        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
//    }
//
//    @RequestMapping(value = "/bankseller",method = {RequestMethod.GET})
//    public String test(){
//        return "bankseller";
//    }
//
//    @RequestMapping(value = "/bankuser",method = {RequestMethod.GET})
//    public String test2(){
//        return "bankuser";
//    }
//
//    @RequestMapping(value = "/bankspread",method = {RequestMethod.GET})
//    public String test3(){
//        return "bankspread";
//    }

    /**
     * 银行列表公开页面（用于APP、浏览器访问）
     * @param model
     * @return
     */
    @RequestMapping(value = "/goBankPub",method = {RequestMethod.GET})
    public String pageBankPub(Model model){
        try {
            Banner banner = new Banner();
            banner.setType(1);
            banner.setStatus(StatusEnum.NORMAL.getVal());
            List<Banner> banners = bannerService.findAll(banner);
            model.addAttribute("banners",banners);
            Bank bank = new Bank();
            bank.setStatus(StatusEnum.NORMAL.getVal());
            List<Bank> banks = bankService.findAll(bank);
            model.addAttribute("banks",banks);
        } catch (Exception e) {
            logger.error("办卡页银行列表查询错误",e);
        }
        return "bankpub";
    }

    /**
     * 银行列表公开页面（用于APP、浏览器访问）
     * @param model
     * @return
     */
    @RequestMapping(value = "/goInputInfo",method = {RequestMethod.GET})
    public String inputInfoPage(@RequestParam(required = false) String bankCode, Model model){
        model.addAttribute("bankCode",bankCode);
        Banner banner = new Banner();
        banner.setType(2);
        banner.setStatus(StatusEnum.NORMAL.getVal());
        try {
            List<Banner> banners = bannerService.findAll(banner);
            model.addAttribute("banner",(banners!=null&&banners.size()>0)?banners.get(0):"");//默认取该类型第一张
        } catch (Exception e) {
            logger.error("用户信息填写页banner加载错误",e);
        }
        return "inputinfo";
    }

    @ResponseBody
    @RequestMapping(value = "/inputInfo",method = RequestMethod.POST)
    public CommonResp inputInfo(@RequestBody @Valid UserVo userVo, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return failureResp().setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        Date curDate = new Date();
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        user.setCreateDate(curDate);
        user.setUpdateDate(curDate);
        user.setStatus(StatusEnum.NORMAL.getVal());
        CommonResp successResp = successResp();
        try {
            userService.addBean(user);
            Bank bank = bankService.getBeanByCode(userVo.getBankCode());
            if (bank!=null){
                successResp.setMessage(bank.getHtmlUrl());
            }
        } catch(GeneralException e){
            logger.warn(e.getMessage());
            return failureResp().setMessage(e.getMessage());
        } catch (Exception e) {
            logger.error("用户申请注册错误",e);
            return failureResp();
        }
        return successResp;
    }

}
