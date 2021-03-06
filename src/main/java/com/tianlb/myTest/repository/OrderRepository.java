package com.tianlb.myTest.repository;

import com.tianlb.myTest.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId
            (Date now, Integer status, Long userId, Pageable pageable);


    Page<Order> findByUserId(Long uid,Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long uid,Integer status,Pageable pageable);

    Optional<Order> findFirstByUserIdAndId(Long uid, Long oid);

    Optional<Order> findFirstByOrderNo(String orderNo);

    @Modifying
    @Query("update Order o\n" +
            "set o.status=:status\n" +
            "where o.orderNo=:orderNo")
    Integer updateStatusByOrderNo(String orderNo,Integer status);

}
