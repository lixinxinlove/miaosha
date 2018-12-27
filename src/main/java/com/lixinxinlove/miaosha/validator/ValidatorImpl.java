package com.lixinxinlove.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


@Component
public class ValidatorImpl implements InitializingBean {


    private Validator validator;

    public ValidatorResult validate(Object bean) {

        ValidatorResult result = new ValidatorResult();

        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);

        if (constraintViolationSet.size() > 0) {
            //有错
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {

                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);

            });
        }

        return result;

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
