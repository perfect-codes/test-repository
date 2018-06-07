package com.flj.miracle.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.flj.miracle.base.domain.Perm;
import com.flj.miracle.base.service.PermService;
import com.flj.miracle.base.web.model.PermModel;

import java.util.Date;

/**
 * Perm的控制层
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
@Controller
public class PermController extends BaseController{

	 @Autowired
	 private PermService permService;
	 /**
	   * 添加
	   */
	 @ResponseBody
	 @RequestMapping(value = { "/perm" }, method = { RequestMethod.POST })
	 public String add(@RequestBody PermModel model, HttpServletRequest request,HttpServletResponse response) {
		  	Perm bean=new Perm();
		  	bindBean(model, bean);
		  	bean.setCreateDate(new Date());
		  	try {
		  		this.permService.addBean(bean);
				return super.success("添加成功!");
			} catch (Exception e) {
				logger.error("Perm添加错误",e);
			}
			return super.failure("添加失败！");
	  }
	  /**
	   * 删除
	   */
	  @RequestMapping(value = { "/perm/{id}" }, method = { RequestMethod.DELETE })
	  public String delete(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) {
			try {
				this.permService.deleteBean(id);
				return super.success("删除成功");
			}  catch (Exception e) {
				logger.error("Perm删除错误",e);
			}
			return super.failure("删除失败！");
	  }
	  /**
	   * 批量删除
	   */
	  @RequestMapping(value = { "/perm/deletes" }, method = { RequestMethod.POST })
	  public String deletes(@RequestParam String cs,HttpServletRequest request, HttpServletResponse response) {
			try {
				this.permService.deleteBeans(cs);
				return super.success("删除成功");
			}  catch (Exception e) {
				logger.error("Customer删除错误",e);
			}
			return super.failure("删除失败！");
	  }
	  /**
	   * 修改
	   */
	  @RequestMapping(value={"/perm/{id}"}, method={ RequestMethod.PUT })
	  public String update(@PathVariable Long id,@RequestBody  PermModel model){
		  try {
			  Perm bean = this.permService.findById(id);
			  this.bindBean(model,bean);
			  this.permService.updateBean(bean);
			  return super.success("修改成功");
		  } catch (Exception e) {
			logger.error("Perm修改错误",e);
		  }
		  return super.failure("修改失败！");
	  }
	  /**
	   * 根据ID获取对象
	   */
	  @RequestMapping(value={"/perm/{id}"}, method={ RequestMethod.GET })
	  public Perm findOne(@PathVariable Long id){
	    try {
	    	Perm bean = this.permService.findById(id);
		    return bean;
		} catch (Exception e) {
			logger.error("Perm单个查询错误",e);
		}
		return null;
	  }
	  /**
	   * 分页查询
	   */
	  @ResponseBody
	  @RequestMapping(value={"/perms"}, method={ RequestMethod.GET, RequestMethod.POST })
	  public Page<Perm> findByPage(@ModelAttribute PermModel model, HttpServletRequest request, HttpServletResponse response){
		  try {
		      Pageable pageable = super.getPageable(request);
		      Page<Perm> beans = this.permService.findByPagination(model, pageable);
		      return beans;
		  } catch (Exception e) {
			  logger.error("Perm分页查询错误",e);
		  }
		  return null;
	  }
}