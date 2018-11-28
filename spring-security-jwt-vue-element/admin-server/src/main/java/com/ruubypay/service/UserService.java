package com.ruubypay.service;

import com.ruubypay.domain.Role;
import com.ruubypay.domain.User;
import com.ruubypay.service.bo.UserMenuBO;
import com.ruubypay.web.dto.UserAddDTO;
import com.ruubypay.web.dto.UserUpdateDTO;
import com.ruubypay.web.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 *
* @Description: User的Service接口类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
public interface UserService{
	/**
	 * 添加
	 * @param dto
	 */
	void addBean(UserAddDTO dto);
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
	void updateBean(UserUpdateDTO dto);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	User findById(long id);
	/**
	 * 查询全部
	 * @return
	 */
	List<User> findAll();
	/**
	 * 分页条件查询
	 * @param model
	 * @param pageable
	 * @return
	 */
	Page<User> findByPagination(UserModel model, Pageable pageable);

	User findByAccountAndPassword(String account, String password);

	/**
	 * 查询用户角色ID列表
	 * @param userId
	 * @return
	 */
	List<Long> findRolesByUserId(Long userId);

	User findByAccount(String account);

	/**
	 * 修改用户角色
	 * @param userId
	 * @param roleIds
	 */
	void updateUserRoles(Long userId,String roleIds);

	/**
	 * 查询用户菜单
	 * @param userId
	 * @return
	 */
	List<UserMenuBO> findUserMenus(long userId);
}