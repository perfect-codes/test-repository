package com.perfectcodes.eurekaclientconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "tipError")
    @Override
    public String sayHello(String name) {
        return restTemplate.getForObject(String.format("http://EUREKA-CLIENT-PRODUCER1/hello?name=%s",name),String.class);
    }

    public String tipError(String name){
        return String.format("Sorry %s,something error for service",name);
    }
}
