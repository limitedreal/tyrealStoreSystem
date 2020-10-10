package com.tyreal.myTest.logic;

import com.tyreal.myTest.core.enumeration.CouponType;
import com.tyreal.myTest.core.money.IMoneyDiscount;
import com.tyreal.myTest.exception.http.ForbiddenException;
import com.tyreal.myTest.exception.http.ParameterException;
import com.tyreal.myTest.model.Coupon;
import com.tyreal.myTest.model.UserCoupon;
import com.tyreal.myTest.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class CouponChecker {

    private Coupon coupon;
    private UserCoupon userCoupon;
    private IMoneyDiscount iMoneyDiscount;


    public CouponChecker(Coupon coupon, UserCoupon userCoupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.userCoupon = userCoupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    public void isOK() {
        Date date = new Date();
        Boolean isIntimeLine = CommonUtil.isInTimeLine(date, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isIntimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    public void finalTotalPriceIsOK(BigDecimal orderFinalTotalPrice
            , BigDecimal serverFinalTotalPrice) {
        //分别是前端传进来的总价和我们计算的总价，比较其中的最终价是否匹配
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverFinalTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    //避免其可能的减到负数的可能性
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverFinalTotalPrice, this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    public void canBeUsed() {

    }

    //public CouponChecker(Long couponId,Long uid) {
    //
    //}
}
