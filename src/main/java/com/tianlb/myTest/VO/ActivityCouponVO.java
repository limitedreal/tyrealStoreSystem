package com.tianlb.myTest.VO;

import com.tianlb.myTest.model.Activity;
import lombok.Getter;
import lombok.Setter;

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
