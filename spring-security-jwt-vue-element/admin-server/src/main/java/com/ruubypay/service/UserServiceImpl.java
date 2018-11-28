package com.ruubypay.service;

import com.ruubypay.common.AssertMessage;
import com.ruubypay.common.constant.UserConstant;
import com.ruubypay.domain.Role;
import com.ruubypay.domain.User;
import com.ruubypay.domain.UserRole;
import com.ruubypay.repository.RolePermRepository;
import com.ruubypay.repository.UserRepository;
import com.ruubypay.repository.UserRoleRepository;
import com.ruubypay.service.bo.UserMenuBO;
import com.ruubypay.web.dto.UserAddDTO;
import com.ruubypay.web.dto.UserUpdateDTO;
import com.ruubypay.web.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Description: User的Service实现类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Value("${app.user.defaultPassword:123}")
    private String defaultPassword;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBean(UserAddDTO dto) {
        User bean = new User();
        BeanUtils.copyProperties(dto, bean);
        bean.setStatus(UserConstant.Status.NORMAL);
        bean.setCreateDate(new Date());
        bean.setPassword(DigestUtils.md5DigestAsHex(defaultPassword.getBytes()));
        this.userRepository.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBean(long id) {
        this.userRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBeans(String ids) {
        if (ids != null) {
            String[] idarr = ids.split(",");
            for (String id : idarr) {
                deleteBean(Long.parseLong(id));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBean(UserUpdateDTO dto) {
        User bean = findById(dto.getId());
        Assert.notNull(bean, AssertMessage.TARGET_IS_NULL);
        BeanUtils.copyProperties(dto, bean);
        bean.setUpdateDate(new Date());
        this.userRepository.save(bean);
    }

    @Override
    public User findById(long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll(getSpecification(null));
    }

    @Override
    public Page<User> findByPagination(UserModel model, Pageable pageable) {
        return this.userRepository.findAll(getSpecification(model), pageable);
    }

    @Override
    public User findByAccountAndPassword(String account, String password) {
        if (Pattern.matches("[\\d]{11}", account)) {
            return this.userRepository.findByPhoneAndPassword(account, password);
        }
        return this.userRepository.findByEmailAndPassword(account, password);
    }

    @Override
    public List<Long> findRolesByUserId(Long userId) {
        List<Long> list = this.userRepository.findRolesByUserId(userId);
//		list.forEach(item -> {
//			Role role = new Role();
//			role.setId(((BigInteger) item[0]).longValue());
//			role.setName((String) item[1]);
//			role.setCode((String) item[2]);
//			roles.add(role);
//		});
        return list;
    }

    @Override
    public User findByAccount(String account) {
        if (Pattern.matches("[\\d]{11}", account)) {
            return this.userRepository.findByPhone(account);
        } else if (account.indexOf("@") > 0) {
            return this.userRepository.findByEmail(account);
        } else {
            return this.userRepository.findByUserName(account);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRoles(Long userId, String roleIds) {
        userRoleRepository.deleteByUserId(userId);
        if (StringUtils.isEmpty(roleIds)){
            return;
        }
        String[] roleIdArray = roleIds.split(",");
        Set<UserRole> userRoles = new HashSet<>(roleIdArray.length);
        for (String roleId : roleIdArray) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(Long.parseLong(roleId));
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        userRoleRepository.save(userRoles);
    }

    @Override
    public List<UserMenuBO> findUserMenus(long userId) {
        List<Object[]> originMenus = userRepository.findMenusByUserId(userId);
        List<UserMenuBO> menus = new ArrayList<>(originMenus.size());
        originMenus.forEach(row -> {
            UserMenuBO userMenuBO = new UserMenuBO();
            userMenuBO.setId(((BigInteger)row[0]).longValue());
            userMenuBO.setCode((String) row[1]);
            userMenuBO.setName((String) row[2]);
            userMenuBO.setLink((String) row[3]);
            userMenuBO.setType(1);
            menus.add(userMenuBO);
        });
        return menus;
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