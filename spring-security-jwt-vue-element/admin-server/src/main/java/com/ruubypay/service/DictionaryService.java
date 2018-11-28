package com.ruubypay.service;

import com.ruubypay.domain.Dictionary;
import com.ruubypay.web.dto.DictionaryAddDTO;
import com.ruubypay.web.dto.DictionaryUpdateDTO;
import com.ruubypay.web.model.DictionaryModel;
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
	 * @param dto
	 */
	void addBean(DictionaryAddDTO dto);
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
	void updateBean(DictionaryUpdateDTO dto);
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