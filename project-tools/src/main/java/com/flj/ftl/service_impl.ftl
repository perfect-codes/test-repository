package ${bean.parentPackageName}.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${bean.packageName}.${bean.className};
import ${bean.parentPackageName}.web.model.${bean.className}Model;
import ${bean.parentPackageName}.repository.${bean.className}Repository;
/**
 *
* @Description: ${bean.className}的Service实现类
* @Author AutoCoder
* @Date ${.now?datetime}
*
 */
@Service("${bean.className?uncap_first}Service")
public class ${bean.className}ServiceImpl implements ${bean.className}Service{

	@Autowired
	private ${bean.className}Repository ${bean.className?uncap_first}Repository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBean(${bean.className} bean){
		this.${bean.className?uncap_first}Repository.save(bean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addBeans(List<${bean.className}> beans){
		this.${bean.className?uncap_first}Repository.saveAll(beans);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBean(long id){
		this.${bean.className?uncap_first}Repository.deleteById(id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteBeans(String ids){
		if(ids!=null){
			String[] idarr = ids.split(",");
			for(String id:idarr){
				deleteBean(Long.parseLong(id));
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateBean(${bean.className} bean){
		this.${bean.className?uncap_first}Repository.save(bean);
	}
	
	@Override
	public ${bean.className} findById(long id){
		return this.${bean.className?uncap_first}Repository.findById(id).orElse(null);
	}
	
	@Override
	public List<${bean.className}> findAll(){
		return this.${bean.className?uncap_first}Repository.findAll(getSpecification(null));
	}
	
	@Override
	public Page<${bean.className}> findByPagination(${bean.className}Model model,Pageable pageable){
		return this.${bean.className?uncap_first}Repository.findAll(getSpecification(model),pageable);
	}
	
	private Specification<${bean.className}> getSpecification(final ${bean.className}Model model) {
		return (Specification<${bean.className}>) (root, query, cb) -> {
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