package com.flj.miracle.base.config;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.service.RoleService;
import com.flj.miracle.base.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ShiroConfiguration {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Bean
    public Realm realm() {
        return new AuthorizingRealm() {
            /**
             * 用于登录验证
             * @param authenticationToken
             * @return
             * @throws AuthenticationException
             */
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                String account = token.getUsername();
                String password = new String(token.getPassword());
                User user = userService.findByAccountAndPassword(account, password);
                if (user != null) {
                    return new SimpleAuthenticationInfo(user,user.getPassword(),user.getTruename());
                }
                return null;
            }

            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                User currentUser = (User) super.getAvailablePrincipal(principalCollection);
                List<String> roleCodes = userService.findRolesByUserId(currentUser.getId()).stream().map(Role::getCode).collect(Collectors.toList());
                List<String> permCodes = roleService.findPermsByRoleCodes(roleCodes).stream().map(Perm::getCode).collect(Collectors.toList());
                SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
                simpleAuthorInfo.addRoles(roleCodes);
                simpleAuthorInfo.addStringPermissions(permCodes);
                return simpleAuthorInfo;
            }
        };
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(realm);
        //用户授权/认证信息Cache, 采用EhCache缓存
//        securityManager.setCacheManager(getEhCacheManager());
        //注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // logged in users with the 'admin' role
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // 放开登录
        chainDefinition.addPathDefinition("/assets/**", "anon");
        chainDefinition.addPathDefinition("/doLogin", "anon");
        chainDefinition.addPathDefinition("/loginView", "anon");
        //记住可访问
        chainDefinition.addPathDefinition("/indexView", "user");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc, roles[admin]");
        return chainDefinition;
    }

    /**
     * 启用缓存
     *
     * @author xpf
     * @date 2018/6/7 上午9:13
     */
    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
