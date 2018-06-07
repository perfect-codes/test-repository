package ${bean.parentPackageName}.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ${bean.packageName}.${bean.className};
/**
 *
 * ${bean.className}的Repository类
 * @Author AutoCoder
 * @Date ${.now?datetime}
 *
 */
@Repository
public interface ${bean.className}Repository extends CrudRepository<${bean.className},Long>,JpaSpecificationExecutor<${bean.className}> {
	
}