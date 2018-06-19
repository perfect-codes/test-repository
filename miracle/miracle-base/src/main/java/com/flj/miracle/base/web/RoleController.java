package com.flj.miracle.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flj.miracle.base.domain.Role;
import com.flj.miracle.core.common.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.service.RoleService;
import com.flj.miracle.base.web.model.RoleModel;

import java.util.Date;
import java.util.Optional;

/**
 * Role的控制层
 *
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Controller
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加/修改
     */
    @ResponseBody
    @RequestMapping(value = {"/role"}, method = {RequestMethod.POST})
    public CommonResp add(@ModelAttribute RoleModel model) {
        Role bean;
        Date now = new Date();
        if (isAdd(model)) {
            try{
                bean = new Role();
                bindBean(model, bean);
                bean.setCreateDate(now);
                this.roleService.addBean(bean);
                return successResp();
            }catch (Exception e){
                logger.error("Role添加错误", e);
            }
        } else if (isDel(model)){
            try {
                this.roleService.deleteBean(model.getId());
                return successResp();
            }catch (Exception e){
                logger.error("Role删除错误", e);
            }
        } else {
            try {
                bean = this.roleService.findById(model.getId());
                bean.setName(model.getName());
                bean.setCode(model.getCode());
                bean.setStatus(model.getStatus());
                bean.setUpdateDate(now);
                this.roleService.updateBean(bean);
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
    @RequestMapping(value = {"/role/{id}"}, method = {RequestMethod.DELETE})
    public String delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.roleService.deleteBean(id);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Role删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = {"/role/deletes"}, method = {RequestMethod.POST})
    public String deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.roleService.deleteBeans(cs);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Customer删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = {"/role/{id}"}, method = {RequestMethod.PUT})
    public String update(@PathVariable Long id, @RequestBody RoleModel model) {
        try {
            Role bean = this.roleService.findById(id);
            this.bindBean(model, bean);
            this.roleService.updateBean(bean);
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("Role修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
    @RequestMapping(value = {"/role/{id}"}, method = {RequestMethod.GET})
    public Role findOne(@PathVariable Long id) {
        try {
            Role bean = this.roleService.findById(id);
            return bean;
        } catch (Exception e) {
            logger.error("Role单个查询错误", e);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @ResponseBody
    @RequestMapping(value = {"/roles"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Page<Role> findByPage(@ModelAttribute RoleModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<Role> beans = this.roleService.findByPagination(model, pageable);
            return beans;
        } catch (Exception e) {
            logger.error("Role分页查询错误", e);
        }
        return null;
    }

    @GetMapping(path = "rolesView")
    public String rolesView() {
        return "rolelist";
    }
}