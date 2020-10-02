package com.tyreal.myTest.DTO.validators;

import com.tyreal.myTest.DTO.TokenGetDTO;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenPasswordValidator  implements ConstraintValidator<TokenPassword, String> {
    private Integer min;
    private Integer max;

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        //为了支持小程序这种没有password的登录方式，此时password为空
        if(StringUtils.isEmpty(str)){
            return true;
        }

        return false;
    }

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }
}
