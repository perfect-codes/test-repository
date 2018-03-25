package com.perfectcodes.service;

import com.perfectcodes.domain.Bank;
import com.perfectcodes.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public void addBean(Bank bean) throws Exception {
        bankRepository.save(bean);
    }

    @Override
    public void delBean(Bank bean) throws Exception {
        bankRepository.delete(bean);
    }

    @Override
    public void updateBean(Bank bean) throws Exception {
        bankRepository.save(bean);
    }

    @Override
    public Bank getBean(Long id) throws Exception {
        return bankRepository.findOne(id);
    }

    @Override
    public List<Bank> findAll(Bank model) throws Exception {
        return bankRepository.findAll(getSpecification(model),new Sort(Sort.Direction.ASC,"indexOrder"));
    }

    @Override
    public Page<Bank> pageBean(final Bank model,final Pageable pageable) throws Exception {
        return bankRepository.findAll(getSpecification(model),pageable);
    }

    @Override
    public Bank getBeanByCode(String code) throws Exception {
        return bankRepository.getByCode(code);
    }

    private Specification getSpecification(final Bank model){
        return new Specification<Bank>() {
            @Override
            public Predicate toPredicate(Root<Bank> root, CriteriaQuery<?> cquery, CriteriaBuilder cbuilder) {
                List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
                Predicate predicate = null;
                if (model.getStatus() != null) {
                    predicate = cbuilder.and(cbuilder.equal(root.<Integer>get("status"), model.getStatus()));
                    andPredicates.add(predicate);
                }
                if (andPredicates.isEmpty()) {
                    return null;
                } else {
                    Predicate predicat = cbuilder.conjunction();
                    predicat.getExpressions().addAll(andPredicates);
                    return predicat;
                }
            }
        };
    }
}
