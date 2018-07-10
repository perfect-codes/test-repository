package com.flj.miracle.base.service;

import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.web.model.UserModel;
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
	 * @param bean
	 */
	void addBean(User bean);
	/**
	 * 批量添加
	 * @param beans
	 * @throws Exception
	 */
	void addBeans(List<User> beans);
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
	void updateBean(User bean);
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

	User findByAccountAndPassword(String account,String password);

	List<Role> findRolesByUserId(Long userId);

	User findByAccount(String account);
}