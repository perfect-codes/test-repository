package com.fj.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fj.log.annotation.AutoLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import java.util.UUID;

/**
 * 日志处理
 *
 * @author xpf
 * @date 2018/9/29 下午10:29
 */
@Slf4j
@Aspect
@Order(1)
public class AutoLogAspect {

    private final String REQUEST_ID = "REQUEST_ID";
    private final String REQUEST_TYPE = "REQUEST_TYPE";

    private final JsonMapper jsonMapper = new JsonMapper();

    @Before(value = "@annotation(autoLog)")
    public void before(JoinPoint joinPoint, AutoLog autoLog) {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(REQUEST_ID, requestId);
        MDC.put(REQUEST_TYPE, autoLog.name());
        Signature st = joinPoint.getSignature();
        log.debug("开始处理|{}.{}",st.getDeclaringTypeName(), st.getName());
        try {
            String requestParam = jsonMapper.writeValueAsString(joinPoint.getArgs());
            log.debug("请求参数|{}", requestParam);
        } catch (JsonProcessingException e) {
            log.error("请求参数处理异常", e);
        }
    }

    @AfterReturning(pointcut = "@annotation(autoLog)", returning = "re")
    public void after(JoinPoint joinPoint, AutoLog autoLog, Object re) {
        Signature st = joinPoint.getSignature();
        try {
            String responseData = jsonMapper.writeValueAsString(re);
            log.debug("返回结果|{}", responseData);
        } catch (JsonProcessingException e) {
            log.error("返回结果处理异常", e);
        } finally {
            log.debug("处理结束|{}.{}", st.getDeclaringTypeName(), st.getName());
            MDC.remove(REQUEST_ID);
            MDC.remove(REQUEST_TYPE);
        }
    }

}
