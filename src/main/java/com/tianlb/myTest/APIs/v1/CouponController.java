package com.tianlb.myTest.APIs.v1;

import com.tianlb.myTest.VO.CouponCategoryVO;
import com.tianlb.myTest.VO.Success;
import com.tianlb.myTest.core.LocalUser;
import com.tianlb.myTest.core.enumeration.CouponStatus;
import com.tianlb.myTest.service.CouponService;
import com.tianlb.myTest.VO.CouponPureVO;
import com.tianlb.myTest.core.interceptors.ScopeLevel;
import com.tianlb.myTest.exception.http.ParameterException;
import com.tianlb.myTest.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("coupon")
@RestController
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getByCategory(
            @PathVariable Long cid) {
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        List<CouponPureVO> vos = CouponPureVO.getList(coupons);
        return vos;
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList() {
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }

    @ScopeLevel()
    @PostMapping("/collect/{id}")
    public Success collectCoupon(@PathVariable(name = "id") Long couponId) {
        Long uid = LocalUser.getUser().getId();
        couponService.collectOneCoupon(uid, couponId);
        return new Success();
    }

    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    List<CouponPureVO> getMyCouponByStatus(@PathVariable Integer status) {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;

        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40003);
        }
        return CouponPureVO.getList(couponList);
    }

    @ScopeLevel()
    @GetMapping("/myself/available/with_category")
    List<CouponCategoryVO> getUserCouponWithCategory() {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(uid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons
                .stream()
                .map(CouponCategoryVO::new)
                .collect(Collectors.toList());
    }

}
