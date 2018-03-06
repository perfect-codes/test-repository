package com.perfectcodes.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController extends BaseController{

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

    @RequestMapping(value = "/random.jpg",method = {RequestMethod.GET})
    public void randomCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        String randomCode = defaultKaptcha.createText();
        BufferedImage bufferedImage = defaultKaptcha.createImage(randomCode);
        session.setAttribute(defaultKaptcha.getConfig().getSessionKey(),randomCode);
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
    }

    @RequestMapping(value = "/bankseller",method = {RequestMethod.GET})
    public String test(){
        return "bankseller";
    }

    @RequestMapping(value = "/bankuser",method = {RequestMethod.GET})
    public String test2(){
        return "bankuser";
    }

    @RequestMapping(value = "/bankspread",method = {RequestMethod.GET})
    public String test3(){
        return "bankspread";
    }

    @RequestMapping(value = "/bank",method = {RequestMethod.GET})
    public String test4(Model model){
        Banner banner = new Banner();
        banner.setType(1);
        banner.setStatus(StatusEnum.NORMAL.getVal());
        List<Banner> banners = null;
        try {
            banners = bannerService.findAll(banner);
        } catch (Exception e) {
            logger.error("办卡页banner列表查询错误",e);
        }
        model.addAttribute("banners",banners);
        //Bank
        Bank bank = new Bank();
        bank.setStatus(StatusEnum.NORMAL.getVal());
        List<Bank> banks = null;
        try {
            banks = bankService.findAll(bank);
        } catch (Exception e) {
            logger.error("办卡页银行列表查询错误",e);
        }
        model.addAttribute("banks",banks);
        return "bank";
    }

}
