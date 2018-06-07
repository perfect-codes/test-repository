package com.flj.miracle.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.service.RoleService;
import com.flj.miracle.base.web.vo.LoginVo;
import com.flj.miracle.core.common.CommonResp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.service.UserService;
import com.flj.miracle.base.web.model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    /**
     * 添加
     */
    @ResponseBody
    @RequestMapping(value = {"/user"}, method = {RequestMethod.POST})
    public String add(@RequestBody UserModel model, HttpServletRequest request, HttpServletResponse response) {
        User bean = new User();
        bindBean(model, bean);
        bean.setCreateDate(new Date());
        try {
            this.userService.addBean(bean);
            return super.success("添加成功!");
        } catch (Exception e) {
            logger.error("User添加错误", e);
        }
        return super.failure("添加失败！");
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

    @ResponseBody
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public CommonResp login(@RequestBody @Valid LoginVo vo, BindingResult br) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(vo.getAccount(), vo.getPassword(), false);
            SecurityUtils.getSubject().login(token);
            return successResp();
        }catch (Exception e){
            logger.error("登录错误",e);
        }
        return failureResp();
    }
}