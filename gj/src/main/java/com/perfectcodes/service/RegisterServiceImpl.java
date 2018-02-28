package com.perfectcodes.service;

import com.perfectcodes.domain.Register;
import com.perfectcodes.repository.RegisterRepository;
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
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository RegisterRepository;

    @Override
    public void addBean(Register bean) throws Exception {
        RegisterRepository.save(bean);
    }

    @Override
    public void delBean(Register bean) throws Exception {
        RegisterRepository.delete(bean);
    }

    @Override
    public void updateBean(Register bean) throws Exception {
        RegisterRepository.save(bean);
    }

    @Override
    public Register getBean(Long id) throws Exception {
        return RegisterRepository.findOne(id);
    }

    @Override
    public Page<Register> pageBean(final Register model,final Pageable pageable) throws Exception {
        return RegisterRepository.findAll(getSpecification(model),pageable);
    }

    private Specification getSpecification(Object model){
        return new Specification<Register>() {
            @Override
            public Predicate toPredicate(Root<Register> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
}
