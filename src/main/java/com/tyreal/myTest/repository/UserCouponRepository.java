package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.User;
import com.tyreal.myTest.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCouponRepository  extends JpaRepository<UserCoupon,Long> {

    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long userId,Long couponId);
}
