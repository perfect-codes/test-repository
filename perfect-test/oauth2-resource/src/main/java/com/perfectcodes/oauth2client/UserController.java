package com.perfectcodes.oauth2client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping("userinfo")
    public String getUserInfo(){
        return "xpf";
    }

    @RequestMapping("users")
    public List<Map<String,String>> listUser(){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>(2);
        for (int i=0;i<2;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("name","xpf");
            map.put("age","29");
            map.put("sex","男");
            list.add(map);
        }
        return list;
    }

}
