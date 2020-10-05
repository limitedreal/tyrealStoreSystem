package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.Category;
import com.tyreal.myTest.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    @Query("select c \n" +
            "from Coupon c\n" +
            "join c.categoryList ca\n" +
            "join Activity a on a.id=c.activityId\n" +
            "where ca.id = :caId \n" +
            "and a.endTime>:now \n" +
            "and a.startTime<:now")
    List<Coupon> findByCategory(Long caId, Date now);
}
