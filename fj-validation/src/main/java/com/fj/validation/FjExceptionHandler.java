package com.fj.validation;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
/**
 * 异常处理
 * @author xpf
 * @date 2019-09-05 18:23
 */
@RestControllerAdvice
public class FjExceptionHandler {
    /**
     * 可根据实际，通过maven集成common包，使用通用返回封装
     * @param request
     * @param t
     * @return
     */
    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, Throwable t){
        StringBuilder sb = new StringBuilder();
        if (t instanceof ConstraintViolationException){
            ConstraintViolationException e = (ConstraintViolationException)t;
            Iterator it = e.getConstraintViolations().iterator();
            while (it.hasNext()){
                ConstraintViolation cv = (ConstraintViolation) it.next();
                sb.append(cv.getMessage());
            }
        }else if (t instanceof BindException){
            BindException e = (BindException)t;
            BindingResult br = e.getBindingResult();
            if (br.hasFieldErrors()){
                sb.append(br.getFieldError().getDefaultMessage());
            }
        }else {
            t.printStackTrace();
            sb.append("参数错误");
        }
        return sb.toString();
    }
}
