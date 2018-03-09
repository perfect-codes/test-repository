package com.perfectcodes.service;

import com.perfectcodes.common.ErrorCodeEnum;
import com.perfectcodes.common.GeneralException;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.domain.Seller;
import com.perfectcodes.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public void addBean(Seller bean) throws Exception {
        if(findByOpenid(bean.getOpenid())!=null){
            sellerRepository.save(bean);
        }else {
            throw new GeneralException(ErrorCodeEnum.ERROR_UNIQUE);
        }
    }

    @Override
    public void delBean(Seller bean) throws Exception {
        sellerRepository.delete(bean);
    }

    @Override
    public void updateBean(Seller bean) throws Exception {
        sellerRepository.save(bean);
    }

    @Override
    public Seller getBean(Long id) throws Exception {
        return sellerRepository.findOne(id);
    }

    @Override
    public Page<Seller> pageBean(final Seller model,final Pageable pageable) throws Exception {
        return sellerRepository.findAll(getSpecification(model),pageable);
    }

    @Override
    public Seller findByOpenid(String openid) throws Exception {
        return sellerRepository.findByOpenid(openid);
    }

    @Override
    public List<Seller> findByLeader(Long leader) throws Exception {
        return sellerRepository.findByLeaderAndStatus(leader, StatusEnum.NORMAL.getVal());
    }

    private Specification getSpecification(Object model){
        return new Specification<Seller>() {
            @Override
            public Predicate toPredicate(Root<Seller> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
}
