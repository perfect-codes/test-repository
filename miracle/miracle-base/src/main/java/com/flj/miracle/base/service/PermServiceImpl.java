package com.flj.miracle.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.web.model.PermModel;
import com.flj.miracle.base.repository.PermRepository;
/**
 *
* @Description: Perm的Service实现类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
@Service("permService")
public class PermServiceImpl implements PermService{

	@Autowired
	private PermRepository permRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBean(Perm bean){
		this.permRepository.save(bean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBeans(List<Perm> beans){
		this.permRepository.saveAll(beans);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBean(long id){
		this.permRepository.deleteById(id);
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
	public void updateBean(Perm bean){
		this.permRepository.save(bean);
	}
	
	@Override
	public Perm findById(long id){
		return this.permRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Perm> findAll(){
		return this.permRepository.findAll(getSpecification(null));
	}
	
	@Override
	public Page<Perm> findByPagination(PermModel model,Pageable pageable){
		return this.permRepository.findAll(getSpecification(model),pageable);
	}
	
	private Specification<Perm> getSpecification(final PermModel model) {
		return (Specification<Perm>) (root, query, cb) -> {
			List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
			Predicate namePredicate = null;
			if (model.getType()!=null){
				namePredicate = cb.and(cb.equal(root.<Integer>get("type"),model.getType()));
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