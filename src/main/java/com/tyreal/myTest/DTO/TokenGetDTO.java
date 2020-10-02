package com.tyreal.myTest.DTO;

import com.tyreal.myTest.DTO.validators.TokenPassword;
import com.tyreal.myTest.core.enumeration.LoginType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenGetDTO {
    @NotBlank(message = "account不允许为空")
    private String account;
    @TokenPassword(max=30, message = "{token.password}")
    private String password;


    private LoginType type;
    //枚举，类型相关宜用枚举
    //登录方式，可能是账号密码/账号验证码/小程序code码(无password)

}