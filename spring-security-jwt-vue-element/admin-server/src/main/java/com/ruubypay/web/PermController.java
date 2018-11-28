package com.ruubypay.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruubypay.common.CommonResp;
import com.ruubypay.domain.Perm;
import com.ruubypay.service.PermService;
import com.ruubypay.web.dto.PermAddDTO;
import com.ruubypay.web.model.PermModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/system")
public class PermController extends BaseController {

    @Autowired
    private PermService permService;

    /**
     * 添加
     */
    @RequestMapping(value = {"/perm"}, method = {RequestMethod.POST})
    public CommonResp add(@RequestBody PermAddDTO dto, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.permService.addPerm(dto);
            return super.success("添加成功!");
        } catch (Exception e) {
            logger.error("Perm添加错误", e);
        }
        return super.failure("添加失败！");
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/perm/{id}"}, method = {RequestMethod.DELETE})
    public CommonResp delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.permService.deleteBean(id);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Perm删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = {"/perm/deletes"}, method = {RequestMethod.POST})
    public CommonResp deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
        try {
            this.permService.deleteBeans(cs);
            return super.success("删除成功");
        } catch (Exception e) {
            logger.error("Customer删除错误", e);
        }
        return super.failure("删除失败！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = {"/perm"}, method = {RequestMethod.PUT})
    public CommonResp update(@RequestBody PermModel model) {
        try {
            Perm bean = this.permService.findById(model.getId());
            if (!bean.getName().equals(model.getName())){
                bean.setName(model.getName());
                this.permService.updateBean(bean);
            }
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("Perm修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
    @RequestMapping(value = {"/perm/{id}"}, method = {RequestMethod.GET})
    public CommonResp findOne(@PathVariable Long id) {
        try {
            Perm bean = this.permService.findById(id);
            return success(bean);
        } catch (Exception e) {
            logger.error("Perm单个查询错误", e);
        }
        return failure();
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = {"/perms"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Page<Perm> findByPage(@ModelAttribute PermModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<Perm> beans = this.permService.findByPagination(model, pageable);
            return beans;
        } catch (Exception e) {
            logger.error("Perm分页查询错误", e);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @PreAuthorize("hasAuthority('menu:read')")
    @RequestMapping(value = {"/menus"}, method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResp findMenuByPage(@ModelAttribute PermModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            model.setType(1);
            Page<Perm> beans = this.permService.findByPagination(model, pageable);
            return success(beans);
        } catch (Exception e) {
            logger.error("菜单分页查询错误", e);
        }
        return null;
    }

}