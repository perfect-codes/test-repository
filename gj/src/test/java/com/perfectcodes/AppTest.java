package com.perfectcodes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = App.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
public class AppTest{

//    @Autowired
//    private WebApplicationContext context;

//    private MockMvc mvc;
//
//    @Before
//    public void initMvc(){
//        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    public void testSupplyInfo() throws Exception{
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("name","徐鹏飞");
//        map.put("telphone","18910359860");
//        map.put("randomCode","1234");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String payload = objectMapper.writeValueAsString(map);
//        this.mvc.perform(post("/supplyInfo").content(payload).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void testRegist() throws Exception{
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("name","徐鹏飞");
//        map.put("telphone","18910359860");
//        map.put("idcard","130723198910160010");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String payload = objectMapper.writeValueAsString(map);
//        this.mvc.perform(post("/regist").content(payload).contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
    @Test
    public void test(){
        String val = File.pathSeparator;
        System.out.println(val);
        String val2 = File.separator;
        System.out.println(val2);
        System.out.println("---aaa---");
    }
}
