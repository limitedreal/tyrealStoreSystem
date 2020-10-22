package com.tianlb.myTest.DTO.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author machenike
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {
    //如果产生错误的话，抛出包含message的exception
    String message() default "两次密码不匹配";
    int min() default 6;
    int max() default 25;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    //关联类
}
