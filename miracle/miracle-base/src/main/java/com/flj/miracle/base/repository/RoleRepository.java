package com.flj.miracle.base.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.flj.miracle.base.domain.Role;

import java.util.List;

/**
 *
 * Role的Repository类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 *
 */
@Repository
public interface RoleRepository extends CrudRepository<Role,Long>,JpaSpecificationExecutor<Role> {
    /**
     * 根据角色编码，查询权限
     * @author xpf
     * @date 2018/6/6 下午6:14
     * @param roleCodes 角色编码List
     * @param permType 权限类型 1操作 2菜单
     * @return
     */
    @Query(value = "SELECT DISTINCT sp.id,sp.name,sp.code,sp.status,sp.type,sp.link,sp.parent_id,sp.level from sys_role_perm srp join sys_perm sp on srp.perm_code = sp.code WHERE sp.type = :permType and srp.role_code IN (:roleCodes)",nativeQuery = true)
    List<Object[]> findMenusByRoleId(@Param("roleCodes") List<String> roleCodes,@Param("permType") int permType);
	
}