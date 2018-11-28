package com.ruubypay.repository;

import com.ruubypay.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * User的Repository类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long>,JpaSpecificationExecutor<User> {

    /**
     * 根据手机号、密码查询
     * @param phone
     * @param password
     * @return
     */
    User findByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);
    /**
     * 根据邮箱、密码查询
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    /**
     * 根据userId查询用户拥有的角色
     * @param userId
     * @return
     */
    @Query(value = "SELECT role_id FROM sys_user_role WHERE user_id = ?1",nativeQuery = true)
    List<Long> findRolesByUserId(Long userId);

    User findByPhone(String phone);

    User findByEmail(String email);

    User findByUserName(String username);

    /**
     * 查询用户菜单
     * @param userId
     * @return
     */
    @Query(value = "SELECT DISTINCT p.id,p.code,p.name,p.link FROM sys_perm p JOIN sys_role_perm rp ON p.id = rp.perm_id JOIN sys_user_role ur ON rp.role_id = ur.role_id AND ur.user_id = ?1 WHERE p.type = 1",nativeQuery = true)
    List<Object[]> findMenusByUserId(long userId);
}