package com.flj.miracle.base.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flj.miracle.base.domain.User;

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
    User findByEmailAndPassword(@Param("email") String email,@Param("password") String password);

    /**
     * 根据userId查询用户拥有的角色
     * @param userId
     * @return
     */
    @Query(value = "SELECT sr.id,sr.name,sr.code,sr.status,sr.create_date,sr.update_date from sys_user_role sur JOIN sys_role sr on sur.role_code = sr.code WHERE user_id = ?1",nativeQuery = true)
    List<Object[]> findRolesByUserId(Long userId);

}