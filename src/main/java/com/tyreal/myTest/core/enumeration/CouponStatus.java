package com.tyreal.myTest.core.enumeration;

import java.util.stream.Stream;

public enum CouponStatus {//

    AVAILABLE(1, "可用，未过期"),
    USED(2, "已使用"),
    EXPIRED(3, "未使用，已过期");

    private Integer value;
    private String description;

    CouponStatus(Integer value, String s) {
        this.value = value;
    }
    public Integer getValue(){
        return this.value;
    }

    //TODO:这里的方法很巧妙，当然是优于在这里写switch的

    public static CouponStatus toType(Integer value){
                        //获取全部的枚举值
        return Stream.of(CouponStatus.values())
                .filter(c->c.value==value)
                .findAny()
                .orElse(null);
    }
}
