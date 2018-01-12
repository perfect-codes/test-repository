package com.perfectcodes.eurekaclientconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String sayHello(String name) {
        return restTemplate.getForObject(String.format("http://EUREKA-CLIENT-PRODUCER1/hello?name=%s",name),String.class);
    }
}
