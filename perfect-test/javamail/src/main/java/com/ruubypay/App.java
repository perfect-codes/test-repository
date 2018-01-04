package com.ruubypay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Bean
    public String send(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("perfect_codes@163.com");
        message.setSubject("test");
        message.setText("just test info");
        message.setTo("perfect_codes@126.com");
        javaMailSender.send(message);
        return "";
    }
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
