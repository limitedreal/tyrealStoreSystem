package com.tyreal.myTest.logic;

import com.tyreal.myTest.BO.SkuOrderBO;
import com.tyreal.myTest.core.enumeration.CouponType;
import com.tyreal.myTest.core.money.IMoneyDiscount;
import com.tyreal.myTest.exception.http.ForbiddenException;
import com.tyreal.myTest.exception.http.ParameterException;
import com.tyreal.myTest.model.Category;
import com.tyreal.myTest.model.Coupon;
import com.tyreal.myTest.model.UserCoupon;
import com.tyreal.myTest.utils.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;


    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
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

    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        //负责校验优惠券的可用性，包括品类、是否达到满减的金额
        //sku price sku_model
        //sku count order
        //sku category id sku_model
        //coupon category
        BigDecimal orderCategoryPrice;
        if (this.coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = this.coupon.getCategoryList()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumPriceByCategoryList(skuOrderBOList, cidList);
        }
        this.couponCanBeUsed(orderCategoryPrice);

    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    private BigDecimal getSumPriceByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        return cidList
                .stream()
                .map(cid -> this.getSumPriceByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    private BigDecimal getSumPriceByCategory(List<SkuOrderBO> skuOrderBOList, Long cid) {
        Optional<BigDecimal> sumOpt = skuOrderBOList
                .stream()
                .filter(sku -> sku.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add);
        return sumOpt.orElse(new BigDecimal("0"));
    }

    //public CouponChecker(Long couponId,Long uid) {
    //
    //}
}
