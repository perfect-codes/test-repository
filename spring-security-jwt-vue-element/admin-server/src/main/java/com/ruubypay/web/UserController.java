package com.ruubypay.web;

import com.ruubypay.common.CommonResp;
import com.ruubypay.domain.Role;
import com.ruubypay.domain.User;
import com.ruubypay.domain.UserRole;
import com.ruubypay.service.RoleService;
import com.ruubypay.service.UserService;
import com.ruubypay.service.bo.RoleTransferBO;
import com.ruubypay.web.dto.LoginDTO;
import com.ruubypay.web.dto.UserAddDTO;
import com.ruubypay.web.dto.UserRoleUpdateDTO;
import com.ruubypay.web.dto.UserUpdateDTO;
import com.ruubypay.web.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/system")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 添加
     */
    @PreAuthorize("hasAuthority('user:create')")
    @RequestMapping(value = {"/user"}, method = {RequestMethod.POST})
    public CommonResp add(@RequestBody UserAddDTO dto) {
        try{
            this.userService.addBean(dto);
            return success();
        }catch (Exception e){
            logger.error("User添加错误", e);
        }
        return failure();
    }

    /**
     * 删除
     */
    @PreAuthorize("hasAuthority('user:delete')")
    @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.DELETE})
    public CommonResp delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
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
    @PreAuthorize("hasAuthority('user:delete')")
    @RequestMapping(value = {"/user/deletes"}, method = {RequestMethod.POST})
    public CommonResp deletes(@RequestParam String cs, HttpServletRequest request, HttpServletResponse response) {
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
    @PreAuthorize("hasAuthority('user:update')")
    @RequestMapping(value = {"/user"}, method = {RequestMethod.PUT})
    public CommonResp update(@RequestBody UserUpdateDTO dto) {
        try {
            this.userService.updateBean(dto);
            return super.success("修改成功");
        } catch (Exception e) {
            logger.error("User修改错误", e);
        }
        return super.failure("修改失败！");
    }

    /**
     * 根据ID获取对象
     */
    @PreAuthorize("hasAuthority('user:read')")
    @RequestMapping(value = {"/user/{id}"}, method = {RequestMethod.GET})
    public CommonResp findOne(@PathVariable Long id) {
        try {
            User bean = this.userService.findById(id);
            return success(bean);
        } catch (Exception e) {
            logger.error("User单个查询错误", e);
        }
        return null;
    }


    /**
     * 分页查询
     */
    @PreAuthorize("hasAuthority('user:read')")
    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResp findByPage(@ModelAttribute UserModel model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Pageable pageable = super.getPageable(request);
            Page<User> beans = this.userService.findByPagination(model, pageable);
            return success(beans);
        } catch (Exception e) {
            logger.error("User分页查询错误", e);
        }
        return null;
    }

    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping(value = {"/user/{id}/roles"}, method = RequestMethod.GET)
    public CommonResp findUserRoles(@PathVariable Long id) {
        try {
            List<Role> originRoles = this.roleService.findAll();
            List<RoleTransferBO> roles = new ArrayList<>(originRoles.size());
            originRoles.forEach(item -> {
                RoleTransferBO bo = new RoleTransferBO();
                bo.setKey(item.getId()).setLabel(item.getName()).setDisabled(item.getStatus()==0);
                roles.add(bo);
            });
            List<Long> userRoles = userService.findRolesByUserId(id);
            Map<String,Object> data = new HashMap<>(2);
            data.put("roles",roles);
            data.put("userRoles",userRoles);
            return success(data);
        } catch (Exception e) {
            logger.error("Role列表查询错误", e);
        }
        return null;
    }

    /**
     * 修改用户角色
     * @return
     */
    @RequestMapping(value = {"/user/{id}/roles"}, method = RequestMethod.PUT)
    public CommonResp updateUserRoles(@PathVariable Long id,@RequestBody UserRoleUpdateDTO dto) {
        try {
            userService.updateUserRoles(id,dto.getRoleIds());
            return success();
        } catch (Exception e) {
            logger.error("修改用户角色", e);
        }
        return null;
    }

}