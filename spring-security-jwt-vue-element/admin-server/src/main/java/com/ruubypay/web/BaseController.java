package com.ruubypay.web;

import com.ruubypay.common.CommonResp;
import com.ruubypay.common.ErrorCodeEnum;
import com.ruubypay.web.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller基类
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
			Pageable pageable = new PageRequest(number,size,sort);
			return pageable;
		}else{
			sort = new Sort(Sort.Direction.fromString(sortOrder), new String[] { "".equals(sortField)?"id":sortField });
			Pageable pageable = new PageRequest(number,size,sort);
			return pageable;
		}
	}

	protected CommonResp success(String message) {
		return new CommonResp(ErrorCodeEnum.SUCCESS).setMessage(message);
	}

	protected CommonResp success(Object data) {
		return new CommonResp(ErrorCodeEnum.SUCCESS).setData(data);
	}

	protected CommonResp failure(String message) {
		return new CommonResp(ErrorCodeEnum.ERROR).setMessage(message);
	}

	protected CommonResp success(){
		return new CommonResp(ErrorCodeEnum.SUCCESS);
	}

	protected CommonResp failure(){
		return new CommonResp(ErrorCodeEnum.ERROR);
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());

}