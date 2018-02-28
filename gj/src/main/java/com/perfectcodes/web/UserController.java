package com.perfectcodes.web;

import com.perfectcodes.common.CommonResp;
import com.perfectcodes.domain.User;
import com.perfectcodes.service.UserService;
import com.perfectcodes.web.vo.UserSupplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/bindUser")
    public String pageIndex(Model model){
        //TODO 此处需要调用微信API获取用户头像等信息
        User user = new User();
        user.setNickname("徐教授");
        user.setOpenid("aaaaaaaaaaaaaaaaaaaaa");
        user.setUnionid("nnnnnnnnnnnnnnnnnn");
        try {
            userService.addBean(user);
        } catch (Exception e) {
            logger.error("用户信息保存错误",e);
        }
        //返回页面显示
        Map<String,Object> userinfo = new HashMap<String, Object>(4);
        userinfo.put("thumb","http://s.amazeui.org/media/i/demos/bing-1.jpg");
        userinfo.put("nickname",user.getNickname());
        userinfo.put("workNumber",String.format("%6d",user.getId()));
        userinfo.put("duty",user.getLevel());
        model.addAttribute("userinfo",userinfo);
        return "bindUser";
    }

    @ResponseBody
    @RequestMapping(value = "/supplyInfo",method = RequestMethod.POST)
    public CommonResp supply(@RequestBody @Valid UserSupplyVo userSupplyVo, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return failureResp().setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        userSupplyVo.getName();
        return successResp();
    }

}
