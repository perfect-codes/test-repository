package com.ruubypay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.sql.DataSource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SecurityApp implements CommandLineRunner
{

//    @Bean
//    public SpringTemplateEngine getSpringTemplateEngine(){
//        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//        springTemplateEngine.setAdditionalDialects(new SpringSecurityDialect());
//        return springTemplateEngine;
//    }

    @Override
    public void run(String... args) throws Exception {

    }
    public static void main( String[] args )
    {
//        SpringApplication.run(App.class,"--debug");
        SpringApplication.run(SecurityApp.class,args);
    }
}
