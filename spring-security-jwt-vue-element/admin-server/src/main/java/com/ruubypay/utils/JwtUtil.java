package com.ruubypay.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class JwtUtil {

    /**
     * 获取token
     * @param username
     * @param secret
     * @return
     */
    public static String getToken(String username,String secret,Collection<? extends GrantedAuthority> authorities){
        List<String> permList = new ArrayList<>(authorities.size());
        String[] perms = new String[authorities.size()];
        authorities.forEach(item -> {
            permList.add(item.getAuthority());
        });
        permList.toArray(perms);
        String token = JWT.create().withSubject(username).withAudience(perms).sign(Algorithm.HMAC256(secret));
        //TODO 增加session逻辑
        return token;
    }

    /**
     * 截取token
     * @param authorization
     * @return
     */
    public static String getToken(String authorization){
        return authorization.replaceFirst("Bearer ","");
    }

    /**
     * 校验token
     * @param authorization
     * @return
     */
    public static String getUsername(String authorization){
//        Algorithm algorithm = Algorithm.HMAC256("secret");
        String username = JWT.decode(getToken(authorization)).getSubject();
        return username;
    }
}
