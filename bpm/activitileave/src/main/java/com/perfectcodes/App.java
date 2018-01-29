package com.perfectcodes;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {

                Group group = identityService.newGroup("user");
                group.setName("users");
                group.setType("security-role");
                identityService.saveGroup(group);

                User admin = identityService.newUser("admin");
                admin.setPassword("admin");
                identityService.saveUser(admin);

                User user1 = identityService.newUser("zhangsan");
                user1.setPassword("123");
                identityService.saveUser(user1);

                User user2 = identityService.newUser("lisi");
                user2.setPassword("123");
                identityService.saveUser(user2);

            }
        };
    }

    @Bean
    public String startProcess(RuntimeService runtimeService){
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("name","zhangsan");
        runtimeService.startProcessInstanceByKey("myProcess_1", vars);
        return "aaa";
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
