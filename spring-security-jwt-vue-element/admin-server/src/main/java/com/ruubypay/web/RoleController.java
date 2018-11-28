package com.ruubypay.web;

import com.ruubypay.common.CommonResp;
import com.ruubypay.domain.Role;
import com.ruubypay.service.PermService;
import com.ruubypay.service.RoleService;
import com.ruubypay.service.bo.RoleMenuBO;
import com.ruubypay.service.bo.RoleUpdateBO;
import com.ruubypay.web.dto.RoleAddDTO;
import com.ruubypay.web.dto.RoleUpdateDTO;
import com.ruubypay.web.model.RoleModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermService permService;

    /**
     * 添加
     */
    @RequestMapping(value = {"/role"}, method = {RequestMethod.POST})
    public CommonResp add(@RequestBody RoleAddDTO dto) {
        try{
            this.roleService.addBean(dto);
            return success();
        }catch (Exception e){
            logger.error("Role添加错误", e);
        }
        return failure();
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/role/{id}"}, method = {RequestMethod.DELETE})
    public CommonResp delete(@PathVariable Long id) {
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
    public CommonResp deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
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
    @RequestMapping(value = {"/role"}, method = {RequestMethod.PUT})
    public CommonResp update(@RequestBody RoleUpdateDTO dto) {
        try {
            this.roleService.updateBean(dto);
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
    public CommonResp findOne(@PathVariable Long id) {
        try {
            Role bean = this.roleService.findById(id);
            RoleUpdateBO bo = new RoleUpdateBO();
            BeanUtils.copyProperties(bean,bo);
            bo.setStatus(bean.getStatus() == 1);
            return success(bo);
        } catch (Exception e) {
            logger.error("Role单个查询错误", e);
        }
        return null;
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = {"/roles"}, method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResp findByPage(@ModelAttribute RoleModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<Role> beans = this.roleService.findByPagination(model, pageable);
            return success(beans);
        } catch (Exception e) {
            logger.error("Role分页查询错误", e);
        }
        return null;
    }

    /**
     * 查询角色权限
     * @param id
     * @return
     */
    @RequestMapping(value = {"/role/{id}/perms"}, method = RequestMethod.GET)
    public CommonResp findByPage(@PathVariable Long id) {
        try {
            List<RoleMenuBO> menus = roleService.findRolePerms(id);
            Map<String,Object> data = new HashMap<>(1);
            data.put("menus",menus);
            return success(data);
        } catch (Exception e) {
            logger.error("查询角色权限错误", e);
        }
        return null;
    }

    /**
     * 设置角色权限
     * @param paramData
     * @return
     */
    @RequestMapping(value = {"/role/{id}/perms"}, method = RequestMethod.POST)
    public CommonResp findByPage(@PathVariable Long id,@RequestBody List<RoleMenuBO> paramData) {
        try {
            List<Long> permList = new ArrayList<>();
            for (RoleMenuBO bo:paramData){
                if (bo.getHadAllPerm()||bo.getHadPartPerm()){
                    permList.add(bo.getId());
                }
                permList.addAll(bo.getHadPermIds());
            }
            roleService.updateRolePerms(id,permList);
            return success();
        } catch (Exception e) {
            logger.error("设置角色权限错误", e);
        }
        return null;
    }

}