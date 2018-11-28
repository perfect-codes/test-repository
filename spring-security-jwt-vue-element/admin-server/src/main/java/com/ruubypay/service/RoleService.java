package com.ruubypay.service;

import com.ruubypay.domain.Perm;
import com.ruubypay.domain.Role;
import com.ruubypay.service.bo.RoleMenuBO;
import com.ruubypay.web.dto.RoleAddDTO;
import com.ruubypay.web.dto.RoleUpdateDTO;
import com.ruubypay.web.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
 *
* @Description: Role的Service接口类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
public interface RoleService{
	/**
	 * 添加
	 * @param dto
	 */
	void addBean(RoleAddDTO dto);
	/**
	 * 删除
	 * @param id
	 */
	void deleteBean(long id);
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBeans(String ids);
	/**
	 * 修改
	 * @param dto
	 */
	void updateBean(RoleUpdateDTO dto);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	Role findById(long id);
	/**
	 * 查询全部
	 * @return
	 */
	List<Role> findAll();
	/**
	 * 分页条件查询
	 * @param model
	 * @param pageable
	 * @return
	 */
	Page<Role> findByPagination(RoleModel model, Pageable pageable);

	/**
	 * 查询角色菜单
	 * @param roleCodes
	 * @return
	 */
	List<Perm> findMenusByRoleId(List<String> roleCodes);

	/**
	 * 查询角色操作权限
	 * @param roleCodes
	 * @return
	 */
	List<Perm> findPermsByRoleCodes(List<String> roleCodes);

	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	List<RoleMenuBO> findRolePerms(long roleId);

	/**
	 * 修改角色权限
	 * @param roleId
	 * @param permIds
	 */
	void updateRolePerms(long roleId, List<Long> permIds);

}