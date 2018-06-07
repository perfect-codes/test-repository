package com.flj.miracle.base.service;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.web.model.PermModel;
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
	 * 添加
	 * @param bean
	 */
	void addBean(Perm bean);
	/**
	 * 批量添加
	 * @param beans
	 * @throws Exception
	 */
	void addBeans(List<Perm> beans);
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