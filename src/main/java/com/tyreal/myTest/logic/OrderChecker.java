package com.tyreal.myTest.logic;

public class OrderChecker {
    // orderService->orderChecker->CouponChecker
    // 这么做的目的是为了让orderService和可能更改的CouponChecker分离
    // 这是设计模式中的facade外观模式

    // 1. 校验order的相关信息
    // 2. 校验Coupon相关信息(CouponChecker)
    // 3. 返回Order的一个模型，使得其写入数据库,即写入数据库的数据必须来源于orderChecker而不是前端

}
