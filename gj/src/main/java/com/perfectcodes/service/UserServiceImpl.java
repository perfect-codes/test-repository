package com.perfectcodes.service;

import com.perfectcodes.common.ErrorCodeEnum;
import com.perfectcodes.common.GeneralException;
import com.perfectcodes.common.StatusEnum;
import com.perfectcodes.domain.User;
import com.perfectcodes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addBean(User bean) throws Exception {
        if (findByIdcardAndBankCode(bean.getIdcard(),bean.getBankCode()) != null) {//已存在
            throw new GeneralException(ErrorCodeEnum.ERROR_UNIQUE);
        }
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
    public User findByIdcardAndBankCode(String idcard, String bankCode) throws Exception {
        return userRepository.findByIdcardAndBankCode(idcard,bankCode);
    }

    @Override
    public Page<User> pageBean(final User model, final Pageable pageable) throws Exception {
        return userRepository.findAll(getSpecification(model), pageable);
    }

    @Override
    public List<User> findBySeller(String seller) {
        return userRepository.findBySellerAndStatus(seller, StatusEnum.NORMAL.getVal());
    }

    private Specification getSpecification(Object model) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }
}
