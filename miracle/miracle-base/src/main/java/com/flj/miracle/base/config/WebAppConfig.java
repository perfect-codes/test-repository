package com.flj.miracle.base.config;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.service.RoleService;
import com.flj.miracle.base.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用来解决shiro框架rememberMe功能，获取不到session问题
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Subject subject = SecurityUtils.getSubject();
                if ((!subject.isAuthenticated())&&subject.isRemembered()&&subject.getSession().getAttribute("user")==null){
                    //加载菜单
                    User currentUser = (User) subject.getPrincipal();
                    List<String> roles = userService.findRolesByUserId(currentUser.getId()).stream().map(Role::getCode).collect(Collectors.toList());
                    List<Perm> menus = roleService.findMenusByRoleId(roles);
                    //最终菜单字符串
                    StringBuffer menusHtml = new StringBuffer("<li class=\"\"><a href=\"/indexView\"><i class=\"menu-icon fa fa-tachometer\"></i><span class=\"menu-text\">系统桌面</span></a><b class=\"arrow\"></b></li>");
                    List<Perm> lv1Menus = menus.stream().filter(perm -> perm.getLevel()==1).collect(Collectors.toList());
                    List<StringBuilder> lv1Htmls = lv1Menus.stream().map(perm -> new StringBuilder("<li><a href='#' class='dropdown-toggle'><i class='menu-icon fa fa-desktop'></i><span class='menu-text'>").append(perm.getName()).append("</span><b class='arrow fa fa-angle-down'></b></a><b class='arrow'></b>")).collect(Collectors.toList());
                    menus.stream().filter(perm -> perm.getLevel()==2).forEach(lv2Menu -> {
                        for (int i=0;i<lv1Menus.size();i++){
                            Perm lv1Menu = lv1Menus.get(i);
                            StringBuilder lv1Html = lv1Htmls.get(i);
                            if (lv2Menu.getParentId().equals(lv1Menu.getId())){
                                int p = 0;
                                if (( p = lv1Html.lastIndexOf("</ul>"))>0){
                                    lv1Html.delete(p,lv1Html.length());
                                }else{
                                    lv1Html.append("<ul class='submenu'>");
                                }
                                lv1Html.append("<li class=''><a href='"+lv2Menu.getLink()+"'><i class='menu-icon fa fa-caret-right'></i>").append(lv2Menu.getName()).append("</a> <b class='arrow'></b></li></ul>");
                                break;
                            }
                        }
                    });
                    lv1Htmls.forEach(item -> {
                        menusHtml.append(item).append("</li>");
                    });
                    Session session = SecurityUtils.getSubject().getSession();
                    session.setAttribute("menusHtml",menusHtml.toString());
                    session.setAttribute("user",currentUser);
                }
                return true;
            }
        }).addPathPatterns("/**");
    }
}
