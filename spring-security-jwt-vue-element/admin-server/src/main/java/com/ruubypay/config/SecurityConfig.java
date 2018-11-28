package com.ruubypay.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ruubypay.common.CommonResp;
import com.ruubypay.common.ErrorCodeEnum;
import com.ruubypay.utils.JsonUtil;
import com.ruubypay.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证
 *
 * @author xpf
 * @date 2018/10/26 下午4:03
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/system/unauthorized").permitAll()
                .antMatchers("/system/login").permitAll()
                .antMatchers("/system/logout").permitAll()
                .antMatchers("/system/info").permitAll()
                .anyRequest().authenticated()
                .and()
//                .antMatchers("/role/**","/user/**").hasRole("ADMIN")
//                .antMatchers("/dictionary/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/**").permitAll()
//                .and().formLogin();
//                .authorizeRequests()
//                .antMatchers("/role/**").hasAuthority("ROLE:READ")
//                .antMatchers("/user/**").hasAuthority("USER:READ")
//                .antMatchers("/dictionary/**").hasAuthority("DICTIONARY:READ")
//                .antMatchers("/system/**").authenticated()
//                .antMatchers("/open/**").permitAll()
//                .and()
                .formLogin()
                .loginPage("/system/unauthorized")
                .loginProcessingUrl("/system/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(((request, response, authentication) -> {
                    Map<String,String> data = new HashMap<>(1);
                    String token = JwtUtil.getToken(authentication.getName(),authentication.getName(),authentication.getAuthorities());
                    data.put("token",token);
                    String json = JsonUtil.toJson(new CommonResp(ErrorCodeEnum.SUCCESS).setMessage("登录成功").setData(data));
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(json);
                }))
                .failureHandler((request, response, e) -> {
                    String json = JsonUtil.toJson(new CommonResp(ErrorCodeEnum.ERROR).setMessage("登录失败"));
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(json);
                })
                .and()
                .exceptionHandling().accessDeniedHandler((request, response, e) -> {
                    String json = JsonUtil.toJson(new CommonResp(ErrorCodeEnum.ERROR_AUTHORITY).setMessage("权限不足"));
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(json);
                })
                .and()
                //验证登录
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .logout().logoutUrl("/system/logout")
                .and().csrf().disable().cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("admin").roles("ADMIN")
//                .and()
//                .withUser("user").password("user").roles("USER");
        //基于权限配置
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("admin").authorities("ROLE::READ","USER::READ","DICTIONARY::READ")
//                .and()
//                .withUser("user").password("user").authorities("USER::READ");
        //基于数据库配置
//        auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
//                .usersByUsernameQuery("SELECT phone as username,password,status as enabled from sys_user WHERE phone = ?")
//                .authoritiesByUsernameQuery("SELECT DISTINCT u.phone AS username, p.name AS authority FROM sys_user u JOIN sys_user_role ur ON u.id = ur.users_id JOIN sys_role r ON r.id = ur.roles_id JOIN sys_role_perm rp ON r.id = rp.roles_id JOIN sys_perm p ON rp.perms_id = p.id WHERE u.phone = ?");
        auth.userDetailsService(userDetailsService);
    }
}

