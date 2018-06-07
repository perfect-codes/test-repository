package com.flj.miracle.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    @RequestMapping(value = "/loginView",method = RequestMethod.GET)
    public String loginView(Model model){
        model.addAttribute("name","xupengfei");
        return "login";
    }
}
