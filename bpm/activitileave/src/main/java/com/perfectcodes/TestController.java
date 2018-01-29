package com.perfectcodes;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/complete")
    public String doAction(@RequestParam String taskId){
        taskService.complete(taskId);
        return HttpStatus.OK.toString();
    }
}
