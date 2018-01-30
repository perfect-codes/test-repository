package com.perfectcodes.web;

import com.perfectcodes.domain.User;
import com.perfectcodes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String pageIndex(Model model) throws Exception {
        model.addAttribute("message","hello");
        return "index";
    }

    @RequestMapping(value = "/user")
    public void addUser() throws Exception {
        User user = new User();
        user.setName("徐鹏飞");
        user.setLevel(1);
        user.setCreateDate(new Date());
        userService.addBean(user);
    }
}
