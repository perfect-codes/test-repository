package com.fj.validation.controller;

import com.fj.validation.constraint.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    /**
     * 单选校验
     * @param type
     * @return
     */
    @GetMapping("testIn")
    public String testIn(@In(value = {"2","3","4"},message = "选择值不合法") Long type){
        System.out.println(type);
        return "ok";
    }

    /**
     * 多选校验
     * @param type
     * @return
     */
    @GetMapping("testInMultiple")
    public String testIn(@In(value = {"2","3","4"},message = "选择值不合法",multiple = true) Long[] type){
        for (Long t:type){
            System.out.println(t);
        }
        return "ok";
    }
}
