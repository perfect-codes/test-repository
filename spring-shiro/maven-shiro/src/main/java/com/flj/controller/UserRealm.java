package com.flj.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * shiro验证核心处理类
 * @author xpf
 * @date 2018/5/12 下午4:45
 */
public class UserRealm extends AuthorizingRealm {

    static Role admin = new Role();
    static Role normal = new Role();
    static User user1 = new User();
    static User user2 = new User();
    static {
        //角色初始化
        admin.setId(1L);
        admin.setName("管理员");
        normal.setId(2L);
        normal.setName("普通用户");
        //user初始化
        user1.setId(1L);
        user1.setUsername("admin");
        user1.setPassword("123");
        user2.setId(2L);
        user2.setUsername("user");
        user2.setPassword("123");
    }

    /**
     * 为当前登录的Subject授予角色和权限
     * @author xpf
     * @date 2018/5/12 下午4:44
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) super.getAvailablePrincipal(principalCollection);
        System.out.println(username);
        List<String> roleList = null;
        List<String> permissionList = null;
        if ("admin".equals(username)){
            roleList = Arrays.asList("admin","zongdui");
            permissionList = Arrays.asList("manager_list","manager_add","manager_del");
        }else if ("user".equals(username)){
            roleList = Arrays.asList("zhandian","user");
            permissionList = Arrays.asList("volunteer_list","manager_list");
        }
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleList);
        simpleAuthorInfo.addStringPermissions(permissionList);
        return simpleAuthorInfo;
    }

    /**
     * 用于登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 实际上这个authenticationToken是从AdminController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        System.out.println("登录验证执行---");
        if (user1.getUsername().equals(username)&&user1.getPassword().equals(password)){
            setSession("user",user1);
            return new SimpleAuthenticationInfo(username,password,username);
        }
        if (user2.getUsername().equals(username)&&user2.getPassword().equals(password)){
            setSession("user",user2);
            return new SimpleAuthenticationInfo(username,password,username);
        }
        return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
