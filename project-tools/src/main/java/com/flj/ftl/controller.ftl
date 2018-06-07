package ${bean.parentPackageName}.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ${bean.packageName}.${bean.className};
import ${bean.parentPackageName}.service.${bean.className}Service;
import ${bean.parentPackageName}.web.model.${bean.className}Model;
/**
 * ${bean.className}的控制层
 * @Author AutoCoder
 * @Date ${.now?datetime}
 */
@Controller
public class ${bean.className}Controller extends BaseController{

	 @Autowired
	 private ${bean.className}Service ${bean.className?uncap_first}Service;
	 /**
	   * 添加
	   */
	 @RequestMapping(value = { "/${bean.className?lower_case}" }, method = { RequestMethod.POST })
	 public String add(@RequestBody ${bean.className}Model model, HttpServletRequest request,HttpServletResponse response) {
		  	${bean.className} bean=new ${bean.className}();
		  	bindBean(model, bean);
		  	try {
		  		this.${bean.className?uncap_first}Service.addBean(bean);
				return super.success("添加成功!");
			} catch (Exception e) {
				logger.error("${bean.className}添加错误",e);
			}
			return super.failure("添加失败！");
	  }
	  /**
	   * 删除
	   */
	  @RequestMapping(value = { "/${bean.className?lower_case}/{id}" }, method = { RequestMethod.DELETE })
	  public String delete(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) {
			try {
				this.${bean.className?uncap_first}Service.deleteBean(id);
				return super.success("删除成功");
			}  catch (Exception e) {
				logger.error("${bean.className}删除错误",e);
			}
			return super.failure("删除失败！");
	  }
	  /**
	   * 批量删除
	   */
	  @RequestMapping(value = { "/${bean.className?lower_case}/deletes" }, method = { RequestMethod.POST })
	  public String deletes(@RequestParam String cs,HttpServletRequest request, HttpServletResponse response) {
			try {
				this.${bean.className?uncap_first}Service.deleteBeans(cs);
				return super.success("删除成功");
			}  catch (Exception e) {
				logger.error("Customer删除错误",e);
			}
			return super.failure("删除失败！");
	  }
	  /**
	   * 修改
	   */
	  @RequestMapping(value={"/${bean.className?lower_case}/{id}"}, method={ RequestMethod.PUT })
	  public String update(@PathVariable Long id,@RequestBody  ${bean.className}Model model){
		  try {
			  ${bean.className} bean = this.${bean.className?uncap_first}Service.findById(id);
			  this.bindBean(model,bean);
			  this.${bean.className?uncap_first}Service.updateBean(bean);
			  return super.success("修改成功");
		  } catch (Exception e) {
			logger.error("${bean.className}修改错误",e);
		  }
		  return super.failure("修改失败！");
	  }
	  /**
	   * 根据ID获取对象
	   */
	  @RequestMapping(value={"/${bean.className?lower_case}/{id}"}, method={ RequestMethod.GET })
	  public ${bean.className} findOne(@PathVariable Long id){
	    try {
	    	${bean.className} bean = this.${bean.className?uncap_first}Service.findById(id);
		    return bean;
		} catch (Exception e) {
			logger.error("${bean.className}单个查询错误",e);
		}
		return null;
	  }
	  /**
	   * 分页查询
	   */
	  @RequestMapping(value={"/${bean.className?lower_case}s"}, method={ RequestMethod.GET, RequestMethod.POST })
	  public Page<${bean.className}> findByPage(@ModelAttribute ${bean.className}Model model, HttpServletRequest request, HttpServletResponse response){
		  try {
		      Pageable pageable = super.getPageable(request);
		      Page<${bean.className}> beans = this.${bean.className?uncap_first}Service.findByPagination(model, pageable);
		      return beans;
		  } catch (Exception e) {
			  logger.error("${bean.className}分页查询错误",e);
		  }
		  return null;
	  }
}