package com.ruubypay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();//用户名
        String passWord = (String) authentication.getCredentials();//密码
        System.out.println(userName+","+passWord);
        MyUserDetails myUserDetails = null;
        try{
            Map<String,Object> user = jdbcTemplate.queryForMap("select * from t_user t where t.username = ?",userName);
//            if (user==null){
//                throw new RuntimeException("用户不存在");
//            }
            if(!((String)user.get("password")).equals(passWord)){
                throw new BadCredentialsException("Password error");
            }
            String perms = jdbcTemplate.queryForObject("SELECT GROUP_CONCAT(p.permcode) FROM t_role_perm rp JOIN t_user u ON rp.role_id = u.id JOIN t_perm p ON rp.perm_id = p.id WHERE u.id = ?",String.class,user.get("id"));
            myUserDetails = new MyUserDetails(userName,passWord,perms);
        }catch (EmptyResultDataAccessException e){
            throw new BadCredentialsException("Username is not exit");
        }
        return new UsernamePasswordAuthenticationToken(myUserDetails,passWord,myUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
