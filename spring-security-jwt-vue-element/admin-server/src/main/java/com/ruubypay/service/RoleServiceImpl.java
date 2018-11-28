package com.ruubypay.service;

import com.ruubypay.common.AssertMessage;
import com.ruubypay.domain.Perm;
import com.ruubypay.domain.Role;
import com.ruubypay.domain.RolePerm;
import com.ruubypay.repository.PermRepository;
import com.ruubypay.repository.RolePermRepository;
import com.ruubypay.repository.RoleRepository;
import com.ruubypay.service.bo.MenuPermBO;
import com.ruubypay.service.bo.RoleMenuBO;
import com.ruubypay.web.dto.RoleAddDTO;
import com.ruubypay.web.dto.RoleUpdateDTO;
import com.ruubypay.web.model.RoleModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.math.BigInteger;
import java.util.*;

/**
 * @Description: Role的Service实现类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RolePermRepository rolePermRepository;
    @Autowired
    PermRepository permRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBean(RoleAddDTO dto) {
        Role bean = new Role();
        BeanUtils.copyProperties(dto, bean);
        bean.setStatus(1);
        this.roleRepository.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBean(long id) {
        this.roleRepository.delete(id);
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
    public void updateBean(RoleUpdateDTO dto) {
        Role bean = findById(dto.getId());
        Assert.notNull(bean, AssertMessage.TARGET_IS_NULL);
        BeanUtils.copyProperties(dto, bean);
        bean.setStatus(dto.getStatus() ? 1 : 0);
        this.roleRepository.save(bean);
    }

    @Override
    public Role findById(long id) {
        return this.roleRepository.findOne(id);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll(getSpecification(null));
    }

    @Override
    public Page<Role> findByPagination(RoleModel model, Pageable pageable) {
        return this.roleRepository.findAll(getSpecification(model), pageable);
    }

    @Override
    public List<Perm> findMenusByRoleId(List<String> roleCodes) {
        List<Perm> perms = new ArrayList<>(roleCodes.size());
        List<Object[]> list = this.roleRepository.findMenusByRoleId(roleCodes, 2);
        list.forEach(item -> {
            Perm perm = new Perm();
            perm.setId(((BigInteger) item[0]).longValue());
            perm.setName((String) item[1]);
            perm.setCode((String) item[2]);
            perm.setType((Integer) item[4]);
            perm.setLink((String) item[5]);
            perm.setParentId(Objects.isNull(item[6]) ? null : ((BigInteger) item[6]).longValue());
            perms.add(perm);
        });
        return perms;
    }

    @Override
    public List<Perm> findPermsByRoleCodes(List<String> roleCodes) {
        List<Perm> perms = new ArrayList<>(roleCodes.size());
        List<Object[]> list = this.roleRepository.findMenusByRoleId(roleCodes, 1);
        list.forEach(item -> {
            Perm perm = new Perm();
            perm.setId(((BigInteger) item[0]).longValue());
            perm.setName((String) item[1]);
            perm.setCode((String) item[2]);
            perm.setType(1);
            perms.add(perm);
        });
        return perms;
    }

    @Override
    public List<RoleMenuBO> findRolePerms(long roleId) {
        List<Object[]> menus = rolePermRepository.findMenusByRoleId(roleId);
        List<RoleMenuBO> list = new ArrayList<>(menus.size());
        menus.forEach(menu -> {
            RoleMenuBO roleMenu = new RoleMenuBO();
            long menuId = ((BigInteger)menu[0]).longValue();
            String menuCode = (String)menu[1];
            String menuName = (String)menu[2];
            Boolean hasMenu = (menu[3]).equals(1);
            roleMenu.setId(menuId);
            roleMenu.setCode(menuCode);
            roleMenu.setName(menuName);
            List<Object[]> perms = rolePermRepository.findPermsByRoleId(roleId,menuId);
            List<MenuPermBO> menuPermList = new ArrayList<>(perms.size());
            List<Long> permIds = new ArrayList<>(perms.size());
            List<Long> hadPermIds = new ArrayList<>(perms.size());
            perms.forEach(perm -> {
                long permId = ((BigInteger)perm[0]).longValue();
                String permCode = (String)perm[1];
                String permName = (String)perm[2];
                Boolean hasPerm = ((Integer)perm[3]) == 1;
                MenuPermBO menuPerm = new MenuPermBO();
                menuPerm.setId(permId);
                menuPerm.setCode(permCode);
                menuPerm.setName(permName);
                menuPermList.add(menuPerm);
                permIds.add(permId);
                if (hasPerm){
                    hadPermIds.add(permId);
                }
            });
            roleMenu.setPerms(menuPermList);
            roleMenu.setPermIds(permIds);
            roleMenu.setHadPermIds(hadPermIds);
            roleMenu.setHadAllPerm(roleMenu.getHadPermIds().size() == roleMenu.getPermIds().size());
            roleMenu.setHadPartPerm(roleMenu.getHadPermIds().size() > 0 && !roleMenu.getHadAllPerm());
            list.add(roleMenu);
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRolePerms(long roleId, List<Long> permIds) {
        rolePermRepository.removeByRoleId(roleId);
        Set<RolePerm> rolePerms = new HashSet<>(permIds.size());
        permIds.forEach(permId -> {
            RolePerm bean = new RolePerm();
            bean.setRoleId(roleId);
            bean.setPermId(permId);
            rolePerms.add(bean);
        });
        rolePermRepository.save(rolePerms);
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