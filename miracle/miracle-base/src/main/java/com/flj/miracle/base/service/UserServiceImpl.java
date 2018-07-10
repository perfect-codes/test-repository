package com.flj.miracle.base.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.flj.miracle.base.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flj.miracle.base.domain.User;
import com.flj.miracle.base.web.model.UserModel;
import com.flj.miracle.base.repository.UserRepository;
/**
 *
* @Description: User的Service实现类
* @Author AutoCoder
* @Date 2018-6-6 15:12:23
*
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBean(User bean){
		this.userRepository.save(bean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBeans(List<User> beans){
		this.userRepository.saveAll(beans);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBean(long id){
		this.userRepository.deleteById(id);
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
	public void updateBean(User bean){
		this.userRepository.save(bean);
	}
	
	@Override
	public User findById(long id){
		return this.userRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<User> findAll(){
		return this.userRepository.findAll(getSpecification(null));
	}
	
	@Override
	public Page<User> findByPagination(UserModel model,Pageable pageable){
		return this.userRepository.findAll(getSpecification(model),pageable);
	}

	@Override
	public User findByAccountAndPassword(String account, String password) {
		if (Pattern.matches("[\\d]{11}",account)){
			return this.userRepository.findByPhoneAndPassword(account,password);
		}
		return this.userRepository.findByEmailAndPassword(account,password);
	}

	@Override
	public List<Role> findRolesByUserId(Long userId) {
		List<Role> roles = new ArrayList<>(5);
		List<Object[]> list = this.userRepository.findRolesByUserId(userId);
		list.forEach(item -> {
			Role role = new Role();
			role.setId(((BigInteger) item[0]).longValue());
			role.setName((String) item[1]);
			role.setCode((String) item[2]);
			role.setStatus((Integer) item[3]);
			roles.add(role);
		});
		return roles;
	}

	@Override
	public User findByAccount(String account) {
		if (Pattern.matches("[\\d]{11}",account)){
			return this.userRepository.findByPhone(account);
		}
		return this.userRepository.findByEmail(account);
	}

	private Specification<User> getSpecification(final UserModel model) {
		return (Specification<User>) (root, query, cb) -> {
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