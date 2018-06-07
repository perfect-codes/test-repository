package ${parentPackageName}.web;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ${parentPackageName}.web.model.BaseModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 所有Controller的父类
 * @Author AutoCoder
 * @Date ${.now?datetime}
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
		String sortField = getStrParamter(request, "sortField", "");
		String sortOrder = getStrParamter(request, "sortOrder", "desc");
		Sort sort = null;
		if (!"".equals(sortField)) {
			sort = new Sort(Sort.Direction.fromString(sortOrder), new String[] { sortField });
		}
		Pageable pageable = new PageRequest(number, size, sort);
		return pageable;
	}

	public void bindBean(BaseModel model, Object entity) {
		try {
			BeanUtils.copyProperties(model,entity);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
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

	protected Logger logger = LoggerFactory.getLogger(getClass());
}