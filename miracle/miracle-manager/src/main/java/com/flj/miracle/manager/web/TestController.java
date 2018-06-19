package com.flj.miracle.manager.web;

import com.flj.miracle.base.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController extends BaseController {

    @GetMapping("test/jqgrid")
    public String jqgrid(){
        return "jqgrid";
    }
}
