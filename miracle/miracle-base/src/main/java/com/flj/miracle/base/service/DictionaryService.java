package com.flj.miracle.base.service;

import com.flj.miracle.base.domain.Dictionary;
import com.flj.miracle.base.web.model.DictionaryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 *
* @Description: Dictionary的Service接口类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
public interface DictionaryService {
	/**
	 * 添加
	 * @param bean
	 */
	void addBean(Dictionary bean);
	/**
	 * 批量添加
	 * @param beans
	 * @throws Exception
	 */
	void addBeans(List<Dictionary> beans);
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
	void updateBean(Dictionary bean);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	Dictionary findById(long id);
	/**
	 * 查询全部
	 * @return
	 */
	List<Dictionary> findAll();
	/**
	 * 分页条件查询
	 * @param model
	 * @param pageable
	 * @return
	 */
	Page<Dictionary> findByPagination(DictionaryModel model, Pageable pageable);

	/**
	 * 查询所有数据字典内容，二级
	 * @return
	 */
	List<Map<String,Object>> findAllSecondLevel();

	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	Dictionary findByName(String name);
}