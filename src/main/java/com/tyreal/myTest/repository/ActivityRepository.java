package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.Activity;
import com.tyreal.myTest.model.Category;
import com.tyreal.myTest.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findOneByName(String name);

    Optional<Activity> findByCouponListId(Long couponId);
}