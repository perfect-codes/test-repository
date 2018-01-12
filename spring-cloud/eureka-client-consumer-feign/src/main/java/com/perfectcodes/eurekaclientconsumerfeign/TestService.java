package com.perfectcodes.eurekaclientconsumerfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(serviceId = "EUREKA-CLIENT-PRODUCER1")
public interface TestService {
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    String sayHello(@RequestParam(name = "name") String name);
}
