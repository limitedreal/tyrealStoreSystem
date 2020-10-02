package com.tyreal.myTest.DTO.validators;

import com.tyreal.myTest.DTO.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//该注解有两个泛型，第一个是关联的注解，第二个是注解修饰的目标的类型,比如打到PersonDTO上

public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {
    private int min;
    private int max;
    private String message;
    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password1 = personDTO.getPassword1();
        String password2 = personDTO.getPassword2();
        boolean match = password1.equals(password2);

        return match;
    }

    //通过这个方法获取注解中的参数

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
        this.message = constraintAnnotation.message();
    }
}
//public class PasswordValidator implements ConstraintValidator<PasswordEqual, String> {
//    @Override
//    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        return false;
//    }
//}
