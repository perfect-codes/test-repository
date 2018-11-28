package com.ruubypay.config;

import com.ruubypay.service.DatabaseService;
import com.ruubypay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 权限验证
 * @author xpf
 * @date 2018/10/27 下午12:27
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private DatabaseService databaseService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ruubypay.domain.User user = userService.findByAccount(username);
        List<Object[]> list = databaseService.getObjectArrayList("SELECT DISTINCT u.user_name AS username, p.code AS authority FROM sys_user u JOIN sys_user_role ur ON u.id = ur.user_id JOIN sys_role r ON r.id = ur.role_id JOIN sys_role_perm rp ON r.id = rp.role_id JOIN sys_perm p ON rp.perm_id = p.id WHERE u.user_name = ?",username);
        Set<GrantedAuthority> set = new HashSet<>(list.size());
        list.forEach(row -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority((String) row[1]);
            set.add(authority);
        });
        UserDetails userDetails = new User(user.getUserName(),user.getPassword(),set);
        return userDetails;
    }
}
