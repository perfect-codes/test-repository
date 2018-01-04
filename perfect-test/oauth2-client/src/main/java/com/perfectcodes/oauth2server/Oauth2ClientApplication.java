package com.perfectcodes.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class Oauth2ClientApplication extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
				.authenticated().and().exceptionHandling();
//				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
//				.logoutSuccessUrl("/").permitAll();
//				.and().csrf()
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//		super.configure(http);
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ClientApplication.class, args);
	}

	@RequestMapping("/")
	public String index(){
		return "hello";
	}

	@RequestMapping("/info")
	public String info(Principal principal){
		return "Y:"+principal.getName();
	}
}
