package com.flj.miracle.base.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flj.miracle.base.web.model.BaseModel;
import com.flj.miracle.core.common.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;

/**
 * 所有Controller的父类
 * @Author AutoCoder
 * @Date 2018-6-6 15:12:23
 */
public class BaseController{

	public String getStrParamter(HttpServletRequest request, String paramName, String defaultValue) {
		String result = request.getParameter(paramName);
		return result == null ? defaultValue : result;
	}

	public Integer getIntParamter(HttpServletRequest request, String paramName, Integer defaultValue) {
		String result = request.getParameter(paramName);
		return result == null ? defaultValue : Integer.parseInt(result);
	}

	public Long getLongParamter(HttpServletRequest request, String paramName, Long defaultValue) {
		String result = request.getParameter(paramName);
		return result == null ? defaultValue : Long.parseLong(result);
	}

	public Double getDoubleParamter(HttpServletRequest request, String paramName, Double defaultValue) {
		String result = request.getParameter(paramName);
		return result == null ? defaultValue : Double.parseDouble(result);
	}

	public Pageable getPageable(HttpServletRequest request) {
		int number = getIntParamter(request, "number", 0);
		int size = getIntParamter(request, "size", 10);
		String sortField = getStrParamter(request, "sidx", "");
		String sortOrder = getStrParamter(request, "sord", "desc");
		Sort sort = null;
		String fieldName = "";
		if (sortField.indexOf(",")>0){
			String[] fields = sortField.split(",");
			for (String field:fields){
				if (field.indexOf(" ")>0){
//					String[] pair = field.split(" ");
//					String key = pair[0];
//					String val = pair[1];
					continue;
				}else {
					fieldName = field.trim();
					break;
				}
			}
			sort = new Sort(Sort.Direction.fromString(sortOrder), new String[] { "".equals(fieldName)?"id":fieldName });
			Pageable pageable = PageRequest.of(number, size, sort);
			return pageable;
		}else{
			sort = new Sort(Sort.Direction.fromString(sortOrder), new String[] { "".equals(sortField)?"id":sortField });
			Pageable pageable = PageRequest.of(number, size, sort);
			return pageable;
		}
	}

	public void bindBean(BaseModel model, Object entity) {
		try {
			BeanUtils.copyProperties(model,entity);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected boolean isAdd(BaseModel model){
		if (OPER_ADD.equals(model.getOper())){
			return true;
		}
		return false;
	}

	protected boolean isDel(BaseModel model){
		if (OPER_DEL.equals(model.getOper())){
			return true;
		}
		return false;
	}

	public String success(String message) {
		return "{\"status\":1,\"message\":\"" + message + "\"}";
	}

	public String success(Collection<?> c) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("message", c);
		ObjectMapper om = new ObjectMapper();
		String result = failure("数据获取失败");
		try {
			result = om.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.error("success方法序列化错误");
		}
		return result;
	}

	public String failure(String message) {
		return "{\"status\":0,\"message\":\"" + message + "\"}";
	}

	protected CommonResp successResp(){
		return new CommonResp().setStatus(1).setMessage("请求成功");
	}

	protected CommonResp failureResp(){
		return new CommonResp().setStatus(0).setMessage("请求超时，请稍后再试");
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String OPER_ADD = "add";
	public static final String OPER_DEL = "del";
}