package com.ruubypay.service;

import com.ruubypay.common.AssertMessage;
import com.ruubypay.domain.Dictionary;
import com.ruubypay.repository.DictionaryRepository;
import com.ruubypay.web.dto.DictionaryAddDTO;
import com.ruubypay.web.dto.DictionaryUpdateDTO;
import com.ruubypay.web.model.DictionaryModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
* @Description: Dictionary的Service实现类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService{

	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBean(DictionaryAddDTO dto){
		Dictionary bean = new Dictionary();
		BeanUtils.copyProperties(dto,bean);
		bean.setCreateDate(new Date());
		this.dictionaryRepository.save(bean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBean(long id){
		this.dictionaryRepository.delete(id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBeans(String ids){
		if(ids!=null){
			String[] idarr = ids.split(",");
			for(String id:idarr){
				deleteBean(Long.parseLong(id));
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateBean(DictionaryUpdateDTO dto){
		Dictionary bean = findById(dto.getId());
		Assert.notNull(bean, AssertMessage.TARGET_IS_NULL);
		if (!bean.getName().equals(dto.getName())){
			bean.setName(dto.getName());
			bean.setUpdateDate(new Date());
			this.dictionaryRepository.save(bean);
		}
	}
	
	@Override
	public Dictionary findById(long id){
		return this.dictionaryRepository.findOne(id);
	}
	
	@Override
	public List<Dictionary> findAll(){
		return this.dictionaryRepository.findAll(getSpecification(null));
	}
	
	@Override
	public Page<Dictionary> findByPagination(DictionaryModel model, Pageable pageable){
		return this.dictionaryRepository.findAll(getSpecification(model),pageable);
	}

	@Override
	public List<Map<String, Object>> findAllSecondLevel() {
		return this.dictionaryRepository.findAllSecondLevel().stream().map(array -> {
			Map<String,Object> map = new HashMap<>(11);
			map.put("id",array[0]);
			map.put("code",array[1]);
			map.put("name",array[2]);
			map.put("status",array[3]);
			map.put("parentId",array[4]);
			map.put("level",array[5]);
			map.put("createDate",array[6]);
			map.put("updateDate",array[7]);
			map.put("isLeaf",array[8]);
			map.put("parentName",array[9]);
			map.put("parentCode",array[10]);
			return map;
		}).collect(Collectors.toList());
	}

	@Override
	public Dictionary findByName(String name) {
		return this.dictionaryRepository.findByName(name);
	}

	private Specification<Dictionary> getSpecification(final DictionaryModel model) {
		return (Specification<Dictionary>) (root, query, cb) -> {
			List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
			Predicate namePredicate;
			if (model.getStatus()!=null){
				namePredicate = cb.and(cb.equal(root.<Integer>get("status"),model.getStatus()));
				andPredicates.add(namePredicate);
			}
			if (model.getLevel()!=null){
				namePredicate = cb.and(cb.equal(root.<Integer>get("level"),model.getLevel()));
				andPredicates.add(namePredicate);
			}
			if (andPredicates.isEmpty()) {
				return null;
			} else {
				Predicate predicate = cb.conjunction();
				predicate.getExpressions().addAll(andPredicates);
				return predicate;
			}
		};
	}
	
}