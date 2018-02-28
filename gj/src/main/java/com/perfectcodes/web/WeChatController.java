package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
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
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
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

    @ResponseBody
    @RequestMapping(value = "/refresh_token",method = {RequestMethod.GET})
    public CommonResp test(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        schedueConfig.getToken();
        logger.info("手动执行刷新access_token成功");
        return successResp();
    }
    /**
     * @author xpf
     * @description 请求用户授权（跳转页面）
     * @date 2018/2/28 10:09
     * @param name=bank|bankUser|bankSeller
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/view/{name}",method = {RequestMethod.GET})
    public String index(@PathVariable String name) throws IOException {
        String state = "bank";
        if (name.equals("bankUser")||name.equals("bankSeller")){
            state = name.toLowerCase();
        }
        String url = String.format("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s/wechat/code2token&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect",propConfig.getAppid(),"http://172.20.1.57:8080",state);
        logger.debug("当前access_token为：[{}]",PropConfig.getAccessToken());
        logger.debug("请求重定向地址为：[{}]",url);
        return url;
    }

    /**
     * @author xpf
     * @description 用户授权后回调(获取用户auth_token)
     * @date 2018/2/28 10:26
     */
    @RequestMapping(value = "/code2token",method = {RequestMethod.GET})
    public String code2Token(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) throws IOException {
        String code = request.getParameter("code");//code参数
        String state = request.getParameter("state");//state参数
        logger.debug(code+","+state);
        HttpGet httpGetToken = new HttpGet(String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", propConfig.getAppid(), propConfig.getSecret(),code));
        HashMap<String,Object> result = GJHttpUtil.get(httpGetToken);
        String token = (String) result.get("access_token");
        String openid = (String) result.get("openid");
        //此处需要调用微信API获取用户头像等信息
        HttpGet httpGetUserinfo = new HttpGet(String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", token, openid));
        HashMap<String,Object> usermap = GJHttpUtil.get(httpGetUserinfo);
        logger.debug(usermap.toString());
        //新增用户
        Date date = new Date();
        User user = new User();
        user.setNickname((String) usermap.get("nickname"));
        user.setSex((Integer) usermap.get("sex"));
        user.setCountry((String) usermap.get("country"));
        user.setProvince((String)usermap.get("province"));
        user.setCity((String) usermap.get("city"));
        user.setOpenid((String) usermap.get("openid"));
        user.setUnionid((String) usermap.get("unionid"));
        user.setLevel(1);
        user.setCreateDate(date);
        user.setUpdateDate(date);
        try {
            userService.addBean(user);
        } catch (Exception e) {
            logger.error("用户信息保存错误",e);
        }
        Map<String,Object> userinfo = new HashMap<String, Object>(4);
        userinfo.put("thumb",usermap.get("headimgurl"));
        userinfo.put("nickname",user.getNickname());
        userinfo.put("workNumber",String.format("%6d",user.getId()));
        userinfo.put("duty",user.getLevel());
        model.addAttribute("userinfo",userinfo);
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
        return state;
    }

}
