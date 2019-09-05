package com.fj.validation.configuration;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 参数校验器配置，快速失败模式，发现一个条件不满足则立即返回
 * @author xpf
 * @date 2019-09-05 16:05
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public Validator getValidator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
