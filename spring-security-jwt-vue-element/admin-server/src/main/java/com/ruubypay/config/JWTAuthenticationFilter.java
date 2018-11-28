package com.ruubypay.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        //判断是否有token
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            chain.doFilter(request,response);
            return;
        }
        String token = authorization.replaceFirst("Bearer ","");
        DecodedJWT decodedJWT = JWT.decode(token);
        List<String> audience = decodedJWT.getAudience();
        Set<GrantedAuthority> authorities = new HashSet<>();
        audience.forEach(item -> {
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item);
            authorities.add(grantedAuthority);
        });

//        JWTAuthenticationToken authenticationToken = new JWTAuthenticationToken(decodedJWT.getSubject(), authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(),null,authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }
}
