package com.ruubypay.web;

import com.ruubypay.common.CommonResp;
import com.ruubypay.common.ErrorCodeEnum;
import com.ruubypay.domain.User;
import com.ruubypay.service.UserService;
import com.ruubypay.utils.JwtUtil;
import com.ruubypay.web.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/login")
//    public CommonResp login(@RequestBody LoginDTO dto, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            User user = this.userService.findByAccount(dto.getUsername());
//            if (user == null){
//                return failure("账号错误");
//            }
//            if (user.getPassword().equals(dto.getPassword())){
//                Map<String,String> data = new HashMap<>(2);
//                data.put("token", user.getUserName());
//                return success(data);
//            }
//            return failure();
//        } catch (Exception e) {
//            logger.error("登录错误", e);
//        }
//        return null;
//    }

    @RequestMapping(value = "/info")
    public CommonResp info(@RequestHeader String Authorization) {
        try {
            System.out.println(Authorization);
            User user = this.userService.findByAccount(JwtUtil.getUsername(Authorization));
            Map<String,Object> data = new HashMap<>(3);
            data.put("name",user.getUserName());
            data.put("avatar",user.getAvatar());
            data.put("roles", Arrays.asList(new String[]{"admin"}));
            data.put("menus",userService.findUserMenus(user.getId()));
            return success(data);
        } catch (Exception e) {
            logger.error("获取用户详情错误", e);
        }
        return null;
    }

    @RequestMapping(value = "/logout")
    public CommonResp logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContextHolder.getContext().getAuthentication();
            return success();
        } catch (Exception e) {
            logger.error("登录错误", e);
        }
        return null;
    }

    @RequestMapping("/unauthorized")
    public CommonResp unauthorized() {
        return new CommonResp(ErrorCodeEnum.ERROR_NO_LOGIN);
    }
}
