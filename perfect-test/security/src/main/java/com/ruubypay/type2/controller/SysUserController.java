package com.ruubypay.type2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecurityPermission;
import java.util.Date;

@Controller
public class SysUserController {

    @RequestMapping("login")
    public ModelAndView login(Model model){
        model.addAttribute("msg","xpf");
        model.addAttribute("url","http://www.baidu.com");
        model.addAttribute("today",new Date());
        return new ModelAndView("login");
    }

    @RequestMapping("/")
    public ModelAndView welcome(Model model){
        return new ModelAndView("index");
    }

}
