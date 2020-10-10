package com.tyreal.myTest.service;

import com.tyreal.myTest.VO.Success;
import com.tyreal.myTest.core.enumeration.CouponStatus;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.exception.http.ParameterException;
import com.tyreal.myTest.model.Activity;
import com.tyreal.myTest.model.Coupon;
import com.tyreal.myTest.model.UserCoupon;
import com.tyreal.myTest.repository.ActivityRepository;
import com.tyreal.myTest.repository.CouponRepository;
import com.tyreal.myTest.repository.UserCouponRepository;
import com.tyreal.myTest.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public Success collectOneCoupon(Long uid, Long couponId) {
        //判断优惠券是否存在(防止篡改)
        this.couponRepository
                .findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));
        //判断活动是否过期(防止边界条件)
        Activity activity = this.activityRepository
                .findByCouponListId(couponId)
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isInTime = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if (!isInTime) {
            throw new ParameterException(40005);
        }
        //判断是否已经领取过了(似乎获得当前可用优惠券的时候就该排除已经领取过了的了吧)
        if (this.userCouponRepository
                .findFirstByUserIdAndCouponId(uid, couponId)
                .isPresent()) {//这里就是要不存在才行，所以此值为真就是有问题的
            throw new ParameterException(40006);
        }
        //完成领取这个动作
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCoupon);
        return new Success();
        // 这里原来是抛出了一个异常，希望能有url，但是真的有必要一定要返回这个url吗
        // 既然成功了，其实有没有什么提示都是没必要的
    }

    public List<Coupon> getMyAvailableCoupons(Long uid){
        Date now = new Date();
        return couponRepository.findMyAvailable(uid,now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid){
        Date now = new Date();
        return couponRepository.findMyUsed(uid,now);
    }
    public List<Coupon> getMyExpiredCoupons(Long uid){
        Date now = new Date();
        return couponRepository.findMyExpired(uid,now);
    }

}
