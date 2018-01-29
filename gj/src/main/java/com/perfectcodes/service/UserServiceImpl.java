package com.perfectcodes.service;

import com.perfectcodes.domain.User;
import com.perfectcodes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addBean(User bean) throws Exception {
        userRepository.save(bean);
    }

    @Override
    public void delBean(User bean) throws Exception {
        userRepository.delete(bean);
    }

    @Override
    public void updateBean(User bean) throws Exception {
        userRepository.save(bean);
    }

    @Override
    public User getBean(Long id) throws Exception {
        return userRepository.findOne(id);
    }

    @Override
    public Page<User> pageBean(final User model,final Pageable pageable) throws Exception {
        return userRepository.findAll(getSpecification(model),pageable);
    }

    private Specification getSpecification(Object model){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
}
