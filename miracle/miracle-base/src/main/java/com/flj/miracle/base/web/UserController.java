package com.flj.miracle.base.web;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.service.RoleService;
import com.flj.miracle.base.service.UserService;
import com.flj.miracle.base.web.model.UserModel;
import com.flj.miracle.base.web.vo.LoginVo;
import com.flj.miracle.core.common.CommonResp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User的控制层
 *
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 添加/删除/修改
     */
    @ResponseBody
    @RequestMapping(value = {"/user"}, method = {RequestMethod.POST})
    public CommonResp add(@ModelAttribute UserModel model) {
        User bean;
        Date now = new Date();
        if (isAdd(model)) {
            try{
                bean = new User();
                bindBean(model, bean);
                bean.setCreateDate(now);
                bean.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
                this.userService.addBean(bean);
                return successResp();
            }catch (Exception e){
                logger.error("User添加错误", e);
            }
        } else if (isDel(model)){
            try {
                this.userService.deleteBean(model.getId());
                return successResp();
            }catch (Exception e){
                logger.error("User删除错误", e);
            }
        } else {
            try {
                bean = this.userService.findById(model.getId());
                bean.setPhone(model.getPhone());
                bean.setTruename(model.getTruename());
                bean.setEmail(model.getEmail());
                bean.setStatus(model.getStatus());
                bean.setGender(model.getGender());
                bean.setIdNumber(model.getIdNumber());
                bean.setUpdateDate(now);
                this.userService.updateBean(bean);
                return successResp();
            }catch (Exception e){
                logger.error("Role修改错误", e);
            }
        }
        return failureResp();
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.DELETE})
    public String delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.userService.deleteBean(id);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("User删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = {"/user/deletes"}, method = {RequestMethod.POST})
    public String deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.userService.deleteBeans(cs);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Customer删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.PUT})
    public String update(@PathVariable Long id, @RequestBody UserModel model) {
        try {
            User bean = this.userService.findById(id);
            this.bindBean(model, bean);
            this.userService.updateBean(bean);
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("User修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
    @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.GET})
    public User findOne(@PathVariable Long id) {
        try {
            User bean = this.userService.findById(id);
            return bean;
        } catch (Exception e) {
            logger.error("User单个查询错误", e);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @ResponseBody
    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Page<User> findByPage(@ModelAttribute UserModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<User> beans = this.userService.findByPagination(model, pageable);
            return beans;
        } catch (Exception e) {
            logger.error("User分页查询错误", e);
        }
        return null;
    }

    /**
     * 跳转登录页面
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = {"/loginView"}, method = RequestMethod.GET)
    public String loginView(Session session, Model model) {
        return "login";
    }

    /**
     * 登录
     * @param vo
     * @param br
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/doLogin"}, method = RequestMethod.POST)
    public CommonResp login(@RequestBody @Valid LoginVo vo, BindingResult br) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(vo.getAccount(), vo.getPassword(), vo.getRememberMe() != null);
            SecurityUtils.getSubject().login(token);
            //加载菜单
            User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
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
            return successResp();
        }catch (Exception e){
            logger.error("登录错误",e);
        }
        return failureResp();
    }

    @RequestMapping(value = {"/doLogout"}, method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @GetMapping(path = "usersView")
    public String usersView(){
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        return "userlist";
    }

    @GetMapping(path = "indexView")
    public String indexView(){
        return "index";
    }

    @GetMapping(path = "settingView")
    public String settingView(){
        return "setting";
    }

    @GetMapping(path = "profileView")
    public String profileView(){
        return "profile";
    }

    @GetMapping(path = "messageView")
    public String messageView(){
        return "message";
    }
}