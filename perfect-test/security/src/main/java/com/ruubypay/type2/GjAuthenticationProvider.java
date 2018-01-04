package com.ruubypay.type2;

import com.ruubypay.MyUserDetails;
import com.ruubypay.type2.domain.SysUser;
import com.ruubypay.type2.repository.SysUserRepository;
import com.ruubypay.type2.repository.SysUserRoleRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class GjAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();//用户名
        String passWord = (String) authentication.getCredentials();//密码
        SysUser sysUser = sysUserRepository.findByUsername(userName);
        if (sysUser==null){
            throw new BadCredentialsException("Username is not exit");
        }
        if(!sysUser.getPassword().equals(DigestUtils.md5Hex(passWord))){
            throw new BadCredentialsException("Password is error");
        }
        String perms = sysUserRoleRepository.findUserPermsByUserId(sysUser.getId());
        sysUser.setPerms(perms);
        return new UsernamePasswordAuthenticationToken(sysUser,passWord,sysUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
