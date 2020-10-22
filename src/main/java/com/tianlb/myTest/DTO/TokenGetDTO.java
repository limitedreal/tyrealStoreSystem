package com.tianlb.myTest.DTO;

import com.tianlb.myTest.DTO.validators.TokenPassword;
import com.tianlb.myTest.core.enumeration.LoginType;
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


    //public LoginType getLoginType() {//我发现这里无法自动转换成enum中的数，所以自定义一个getter来帮助转换
    //    return LoginType.int2Enum(this.loginType);
    //}
}