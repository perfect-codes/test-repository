package ${bean.parentPackageName}.service;

import ${bean.packageName}.${bean.className};
import ${bean.parentPackageName}.web.model.${bean.className}Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 *
* @Description: ${bean.className}的Service接口类
* @Author AutoCoder
* @Date ${.now?datetime}
*
 */
public interface ${bean.className}Service{
	/**
	 * 添加
	 * @param bean
	 */
	void addBean(${bean.className} bean);
	/**
	 * 批量添加
	 * @param beans
	 * @throws Exception
	 */
	void addBeans(List<${bean.className}> beans);
	/**
	 * 删除
	 * @param id
	 */
	void deleteBean(long id);
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBeans(String ids);
	/**
	 * 修改
	 * @param bean
	 */
	void updateBean(${bean.className} bean);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	${bean.className} findById(long id);
	/**
	 * 查询全部
	 * @return
	 */
	List<${bean.className}> findAll();
	/**
	 * 分页条件查询
	 * @param model
	 * @param pageable
	 * @return
	 */
	Page<${bean.className}> findByPagination(${bean.className}Model model, Pageable pageable);
}