package com.flj.miracle.base.service;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.web.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
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
	 * @param bean
	 */
	void addBean(Role bean);
	/**
	 * 批量添加
	 * @param beans
	 * @throws Exception
	 */
	void addBeans(List<Role> beans);
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
	 * @param bean
	 */
	void updateBean(Role bean);
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


}