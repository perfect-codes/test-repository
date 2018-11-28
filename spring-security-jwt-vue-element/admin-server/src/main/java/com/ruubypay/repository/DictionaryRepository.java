package com.ruubypay.repository;

import com.ruubypay.domain.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Dictionary的Repository类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 *
 */
@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary,Long>,JpaSpecificationExecutor<Dictionary> {

    @Query(value = "SELECT sd1.*,sd2.name parentName,sd2.code parentCode from sys_dictionary sd1 JOIN sys_dictionary sd2 on sd1.parent_id = sd2.id WHERE sd1.status = 1 and sd1.level = 2",nativeQuery = true)
    List<Object[]> findAllSecondLevel();

    Dictionary findByName(String name);
}