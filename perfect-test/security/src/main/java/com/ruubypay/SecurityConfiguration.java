package com.ruubypay;

import com.ruubypay.type2.GjAuthenticationProvider;
import com.ruubypay.type2.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private GjAuthenticationProvider provider;
//    @Autowired
//    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource);
        auth.authenticationProvider(provider);
//        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/*.html").permitAll()
//                .antMatchers("/*.css").permitAll()
//                .antMatchers("/messages").hasAuthority("MESSAGE_SEARCH")//.hasRole("MESSAGE_MNG")
                .antMatchers("/messages").permitAll()
                .antMatchers("/message/add").hasAuthority("MESSAGE_ADD")//.hasRole("USER_MNG")
                .antMatchers("/message/del").hasAuthority("MESSAGE_DELETE")//.hasRole("USER_MNG")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=true")
//                .successForwardUrl("/welcome")
                .and()
                .logout()
                .permitAll();
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .clearAuthentication(true);
//        super.configure(http);
    }
}
