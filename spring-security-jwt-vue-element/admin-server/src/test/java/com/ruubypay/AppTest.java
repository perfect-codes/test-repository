package com.ruubypay;

import static org.junit.Assert.assertTrue;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void getToken(){

        String token = JWT.create().withSubject("xpf").withIssuer("user").withClaim("date",new Date()).sign(Algorithm.HMAC256("123"));
        System.out.println(token);
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC384("xpf")).build();
//        DecodedJWT jwt = verifier.verify(token);
//        System.out.println(jwt.getHeader());
//        System.out.println(jwt.getPayload());
//        System.out.println(jwt.getSignature());
//        System.out.println(jwt.getToken());
        DecodedJWT decodedJWT = JWT.decode(token);
        System.out.println(decodedJWT.getHeader());
        System.out.println(decodedJWT.getPayload());
        System.out.println(decodedJWT.getSignature());
        System.out.println(decodedJWT.getToken());
        System.out.println(decodedJWT.getSubject());
        System.out.println(decodedJWT.getContentType());
        System.out.println(decodedJWT.getClaim("date"));
        System.out.println(decodedJWT.getIssuer());
    }
}
