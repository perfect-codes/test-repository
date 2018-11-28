package com.ruubypay.service;

import com.ruubypay.domain.Perm;
import com.ruubypay.web.dto.MenuAddDTO;
import com.ruubypay.web.dto.PermAddDTO;
import com.ruubypay.web.model.PermModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 *
* @Description: Perm的Service接口类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
public interface PermService{
	/**
	 * 添加菜单
	 * @param dto
	 */
	void addMenu(MenuAddDTO dto);
	/**
	 * 添加权限
	 * @param dto
	 */
	void addPerm(PermAddDTO dto);
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
	void updateBean(Perm bean);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	Perm findById(long id);
	/**
	 * 查询全部
	 * @return
	 */
	List<Perm> findAll();
	/**
	 * 分页条件查询
	 * @param model
	 * @param pageable
	 * @return
	 */
	Page<Perm> findByPagination(PermModel model, Pageable pageable);

}