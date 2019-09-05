package com.fj.validation.validator;

import com.fj.validation.constraint.In;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;

/**
 * 选择类合法校验
 * @author xpf
 * @date 2019-09-05 17:34
 */
public class InValidator implements ConstraintValidator<In,Object> {

    private In c;

    @Override
    public void initialize(In constraintAnnotation) {
        c = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String[] values = c.value();
        if (c.multiple()){
            Object[] array = (Object[]) o;
            for (Object item: array){
                if (!exist(item.toString(), values)){
                    return false;
                }
            }
        }else {
            if (!exist(o.toString(), values)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定值是否在数组内
     * @param v
     * @param options
     * @return
     */
    private boolean exist(String v, String[] options){
        if (options == null || options.length == 0 || v == null){
            return false;
        }
        for (String option: options){
            if (option.equals(v)){
                return true;
            }
        }
        return false;
    }
}
