package com.ruubypay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    @RequestMapping(value = "messages")
    public String listMessage(){
        return "message";
    }

    @RequestMapping(value = "message/add")
    public String addMessage(){
        return "user success";
    }

    @RequestMapping(value = "message/delete")
    public String deleteMessage(){
        return "logout success";
    }
}
