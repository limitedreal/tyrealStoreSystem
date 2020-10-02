package com.tyreal.myTest.DTO.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author tyreal
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {

    String message() default "字段不符合要求";

    int min() default 6;

    int max() default 25;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
