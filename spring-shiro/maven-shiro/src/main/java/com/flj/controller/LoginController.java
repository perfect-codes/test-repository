package com.flj.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "system")
public class LoginController {

    @RequestMapping(value = "login_page")
    public ModelAndView pageLogin(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value = "login")
    public String pageLogin(@RequestParam String username,@RequestParam String password,Model model){
//        if ("xpf".equals(username)){
////            mv.setViewName("desktop");
////            mv.addObject("username",username);
//            return "redirect:/system/desktop";
//        }else{
//            model.addAttribute("errorMsg","账号或密码错误");
//            return "login_page";
//        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        SecurityUtils.getSubject().login(token);
        return "redirect:/system/desktop";
    }

    @RequestMapping(value = "desktop")
    public ModelAndView pageDesktop(){
        ModelAndView mv = new ModelAndView("desktop");
        return mv;
    }

    @RequestMapping(value = "manager_list")
    public String pageManagers(){
        return "manager_list";
    }

    @RequestMapping(value = "manager_add")
    public String pageManagerAdd(){
        return "manager_add";
    }

    @RequestMapping(value = "fail")
    public String pageFail(){
        return "fail";
    }

    @ResponseBody
    @RequestMapping(value = "ini")
    public void ini(){
        //1.
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro111.ini.bak");
//
//        //2.
//        SecurityManager securityManager = factory.getInstance();
//
//        //3.
//        SecurityUtils.setSecurityManager(securityManager);
//
//        Subject currentUser = SecurityUtils.getSubject();
//
//        if ( !currentUser.isAuthenticated() ) {
//            //collect user principals and credentials in a gui specific manner
//            //such as username/password html form, X509 certificate, OpenID, etc.
//            //We'll use the username/password example here since it is the most common.
//            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
//
//            //this is all you have to do to support 'remember me' (no config - built in!):
////            token.setRememberMe(true);
//
//            try {
//                currentUser.login( token );
//                //if no exception, that's it, we're done!
//            } catch ( UnknownAccountException uae ) {
//                //username wasn't in the system, show them an error message?
//                uae.printStackTrace();
//            } catch ( IncorrectCredentialsException ice ) {
//                //password didn't match, try again?
//                ice.printStackTrace();
//            } catch ( LockedAccountException lae ) {
//                //account for that username is locked - can't login.  Show them a message?
//                lae.printStackTrace();
//            } catch ( AuthenticationException ae ) {
//                //unexpected condition - error?
//                ae.printStackTrace();
//            }
//        }else {
//            System.out.println(currentUser.getPrincipal());
//        }

//        Session session = currentUser.getSession();
//
//        session.setAttribute( "someKey", "aValue" );
//
//        String a = (String) session.getAttribute("someKey");
//
    }


    @ResponseBody
    @RequestMapping(value = "/userlist")
    public String listUser(){
        return "hha";
    }
}
