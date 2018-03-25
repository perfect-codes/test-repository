package com.perfectcodes.service;

import com.perfectcodes.domain.Banner;
import com.perfectcodes.repository.BannerRepository;
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
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public void addBean(Banner bean) throws Exception {
        bannerRepository.save(bean);
    }

    @Override
    public void delBean(Banner bean) throws Exception {
        bannerRepository.delete(bean);
    }

    @Override
    public void updateBean(Banner bean) throws Exception {
        bannerRepository.save(bean);
    }

    @Override
    public Banner getBean(Long id) throws Exception {
        return bannerRepository.findOne(id);
    }

    @Override
    public List<Banner> findAll(Banner model) throws Exception {
        return bannerRepository.findAll(getSpecification(model), new Sort(Sort.Direction.ASC, "indexOrder"));
    }

    @Override
    public Page<Banner> pageBean(final Banner model, final Pageable pageable) throws Exception {
        return bannerRepository.findAll(getSpecification(model), pageable);
    }

    private Specification getSpecification(final Banner model) {
        return new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> cquery, CriteriaBuilder cbuilder) {

                List<Expression<Boolean>> andPredicates = new ArrayList<Expression<Boolean>>();
                Predicate predicate = null;
                if (model.getStatus() != null) {
                    predicate = cbuilder.and(cbuilder.equal(root.<Integer>get("status"), model.getStatus()));
                    andPredicates.add(predicate);
                }
                if (model.getType() != null) {
                    predicate = cbuilder.and(cbuilder.equal(root.<Integer>get("type"), model.getType()));
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
