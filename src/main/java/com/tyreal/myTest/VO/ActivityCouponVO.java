package com.tyreal.myTest.VO;

import com.tyreal.myTest.model.Activity;
import com.tyreal.myTest.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO{
    private List<CouponPureVO> couponList;

    public ActivityCouponVO(Activity activity) {
        super(activity);
        couponList = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .collect(Collectors.toList());
    }
}
