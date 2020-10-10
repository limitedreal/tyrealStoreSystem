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

    @Query("select c \n" +
            "from Coupon c\n" +
            "where c.wholeStore=:isWholeStore \n" +
            "and c.endTime>:now \n" +
            "and c.startTime<:now\n")
    List<Coupon> findByWholeStore(Boolean isWholeStore,Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id=uc.couponId\n" +
            "where" +
            " " +
            "uc.userId=:uid\n" +
            "and uc.status = 1\n" +
            "and c.startTime < :now \n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null")
    List<Coupon> findMyAvailable(Long uid,Date now);

    //这里orderid和status都校验了，这样是为了严谨，但是真的好吗？
    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id=uc.couponId\n" +
            "where" +
            " " +
            "uc.userId=:uid\n" +
            "and uc.status = 2\n" +
            "and c.startTime < :now \n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is not null")
    List<Coupon> findMyUsed(Long uid,Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id=uc.couponId\n" +
            "where" +
            " " +
            "uc.userId=:uid\n" +
            "and c.endTime < :now\n" +
            "and uc.status <> 2\n" +
            "and uc.orderId is null")
    List<Coupon> findMyExpired(Long uid,Date now);

}
