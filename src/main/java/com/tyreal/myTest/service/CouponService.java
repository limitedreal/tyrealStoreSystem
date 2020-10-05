package com.tyreal.myTest.service;

import com.tyreal.myTest.model.Coupon;
import com.tyreal.myTest.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public List<Coupon> getByCategory(Long cid){
        Date now = new Date();
        return couponRepository.findByCategory(cid,now);
    }

}
