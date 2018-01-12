package com.perfectcodes.eurekaclient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(required = false,defaultValue = "hehe") String name) {
        return String.format("Hello %s!", name);
    }
}
