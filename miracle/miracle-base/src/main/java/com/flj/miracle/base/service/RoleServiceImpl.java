package com.flj.miracle.base.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.flj.miracle.base.domain.Perm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flj.miracle.base.domain.Role;
import com.flj.miracle.base.web.model.RoleModel;
import com.flj.miracle.base.repository.RoleRepository;
/**
 *
* @Description: Role的Service实现类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBean(Role bean){
		this.roleRepository.save(bean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBeans(List<Role> beans){
		this.roleRepository.saveAll(beans);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBean(long id){
		this.roleRepository.deleteById(id);
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
	public void updateBean(Role bean){
		this.roleRepository.save(bean);
	}
	
	@Override
	public Role findById(long id){
		return this.roleRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Role> findAll(){
		return this.roleRepository.findAll(getSpecification(null));
	}
	
	@Override
	public Page<Role> findByPagination(RoleModel model,Pageable pageable){
		return this.roleRepository.findAll(getSpecification(model),pageable);
	}

	@Override
	public List<Perm> findMenusByRoleId(List<String> roleCodes) {
		List<Perm> perms = new ArrayList<>(roleCodes.size());
		List<Object[]> list = this.roleRepository.findMenusByRoleId(roleCodes,2);
		list.forEach(item -> {
			Perm perm = new Perm();
			perm.setId(((BigInteger) item[0]).longValue());
			perm.setName((String) item[1]);
			perm.setCode((String) item[2]);
			perm.setStatus((Integer) item[3]);
			perm.setType((Integer) item[4]);
			perm.setLink((String) item[5]);
			perm.setParentId(Objects.isNull(item[6])?null:((BigInteger) item[6]).longValue());
			perm.setLevel((Integer) item[7]);
			perms.add(perm);
		});
		return perms;
	}

	@Override
	public List<Perm> findPermsByRoleCodes(List<String> roleCodes) {
		List<Perm> perms = new ArrayList<>(roleCodes.size());
		List<Object[]> list = this.roleRepository.findMenusByRoleId(roleCodes,1);
		list.forEach(item -> {
			Perm perm = new Perm();
			perm.setId(((BigInteger) item[0]).longValue());
			perm.setName((String) item[1]);
			perm.setCode((String) item[2]);
			perm.setStatus((Integer) item[3]);
			perm.setType(1);
			perms.add(perm);
		});
		return perms;
	}

	private Specification<Role> getSpecification(final RoleModel model) {
		return (Specification<Role>) (root, query, cb) -> {
			List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
			Predicate namePredicate = null;
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