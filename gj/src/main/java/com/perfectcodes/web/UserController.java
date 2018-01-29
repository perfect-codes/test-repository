package com.perfectcodes.web;

import com.perfectcodes.domain.User;
import com.perfectcodes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user")
    public void addUser() throws Exception {
        User user = new User();
        user.setName("徐鹏飞");
        user.setLevel(1);
        user.setCreateDate(new Date());
        userService.addBean(user);
    }
}
