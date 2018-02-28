package com.perfectcodes.service;

import com.perfectcodes.domain.Affix;
import com.perfectcodes.repository.AffixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class AffixServiceImpl implements AffixService {

    @Autowired
    private AffixRepository affixRepository;

    @Override
    public void addBean(Affix bean) throws Exception {
        affixRepository.save(bean);
    }

    @Override
    public void delBean(Affix bean) throws Exception {
        affixRepository.delete(bean);
    }

    @Override
    public void updateBean(Affix bean) throws Exception {
        affixRepository.save(bean);
    }

    @Override
    public Affix getBean(Long id) throws Exception {
        return affixRepository.findOne(id);
    }

    @Override
    public List<Affix> findAll(Affix model) throws Exception {
        return affixRepository.findAll(getSpecification(model),new Sort(Sort.Direction.ASC,"indexOrder"));
    }

    @Override
    public Page<Affix> pageBean(final Affix model,final Pageable pageable) throws Exception {
        return affixRepository.findAll(getSpecification(model),pageable);
    }

    private Specification getSpecification(final Affix model){
        return new Specification<Affix>() {
            @Override
            public Predicate toPredicate(Root<Affix> root, CriteriaQuery<?> cquery, CriteriaBuilder cbuilder) {
                Predicate predicate = null;
                if (model.getStatus()!=null){
                    predicate = cbuilder.and(cbuilder.equal(root.<Integer>get("status"),model.getStatus()));
                }
                return predicate;
            }
        };
    }
}
