package com.tianlb.myTest.repository;

import com.tianlb.myTest.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findOneByName(String name);

    Optional<Activity> findByCouponListId(Long couponId);
}