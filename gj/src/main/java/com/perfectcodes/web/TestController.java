package com.perfectcodes.web;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController{

    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    public String test(){
        logger.debug("------测试日志------");
        logger.info("--------INFO级别日志---------");
        return "test ok";
    }
}
