package com.perfectcodes.oauth2client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/")
    public String getUserInfo(Principal principal){
        return "username:"+principal.getName();
    }

    @RequestMapping("/login3")
    public String welcome(){
//        oAuth2RestTemplate.getForObject("userinfo",String.class);
//        oAuth2RestTemplate.getAccessToken();
        return "xpf";
    }


}
