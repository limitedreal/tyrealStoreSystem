package com.tyreal.myTest.DTO;

import com.tyreal.myTest.DTO.validators.PasswordEqual;
import lombok.*;
import org.hibernate.validator.constraints.Length;

// 数据传输对象

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor

@Builder
@Getter
@PasswordEqual(message = "PersonDTO，校验错误",min = 4)
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    @Length(min = 2,max = 7,message = "校验错误")
    private String name;
    private Integer age;

    //@PasswordEqual

    private String password1;

    private String password2;
}
