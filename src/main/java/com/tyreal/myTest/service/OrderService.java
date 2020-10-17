package com.tyreal.myTest.service;

import com.tyreal.myTest.DTO.OrderDTO;
import com.tyreal.myTest.DTO.SkuInfoDTO;
import com.tyreal.myTest.core.money.IMoneyDiscount;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.logic.CouponChecker;
import com.tyreal.myTest.model.Coupon;
import com.tyreal.myTest.model.Sku;
import com.tyreal.myTest.model.UserCoupon;
import com.tyreal.myTest.repository.CouponRepository;
import com.tyreal.myTest.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    public void isOk(Long uid, OrderDTO orderDTO) {
        //订单校验的主方法
        //if(orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <=0){
        //
        //}//利用注释校验过了
        List<Long> skuIdList = orderDTO
                .getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());
        List<Sku> skuList = skuService.getSkuByIds(skuIdList);

        Long couponId = orderDTO.getCouponId();
        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40003));
            UserCoupon userCoupon = this.userCouponRepository
                    .findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(50006));
            CouponChecker couponChecker = new CouponChecker(coupon,iMoneyDiscount);

        }
    }
}
