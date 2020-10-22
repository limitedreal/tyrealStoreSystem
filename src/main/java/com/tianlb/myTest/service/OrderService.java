package com.tianlb.myTest.service;

import com.tianlb.myTest.DTO.OrderDTO;
import com.tianlb.myTest.DTO.SkuInfoDTO;
import com.tianlb.myTest.core.LocalUser;
import com.tianlb.myTest.core.enumeration.OrderStatus;
import com.tianlb.myTest.core.money.IMoneyDiscount;
import com.tianlb.myTest.exception.http.ForbiddenException;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.exception.http.ParameterException;
import com.tianlb.myTest.logic.CouponChecker;
import com.tianlb.myTest.logic.OrderChecker;
import com.tianlb.myTest.model.*;
import com.tianlb.myTest.repository.CouponRepository;
import com.tianlb.myTest.repository.OrderRepository;
import com.tianlb.myTest.repository.SkuRepository;
import com.tianlb.myTest.repository.UserCouponRepository;
import com.tianlb.myTest.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Value("${myTest.order.max-sku-limit}")
    private Integer maxSkuLimit;

    @Value("${myTest.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    //这里面修改了很多表的数据，而且都是更新和插入，连续地更新和插入如果不使用事务，就会出现很多危险的问题
    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar expiredTime = Calendar.getInstance();
        Calendar now = (Calendar) expiredTime.clone();
        expiredTime.add(Calendar.SECOND, this.payTimeLimit);
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                //这里可以使用前端数据，是因为已经校验过了
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                //.snapAddress(orderDTO.getAddress())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime.getTime())
                .build();
        //在builder中无法使用我们自定义覆盖的setter，所以要后面手动set
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        //order.setCreateTime(now.getTime());
        order.setPlacedTime(now.getTime());

        this.orderRepository.save(order);
        // 减库存
        this.reduceStock(orderChecker);
        // 核销优惠券
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        // TODO:加入延迟消息队列

        return order.getId();
    }

    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = this.userCouponRepository.writeOff(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku ordersku : orderSkuList) {
            //checker里面检测过一次库存了，但是这里必须再检测一次，不然还是有可能出问题
            int result = this.skuRepository.reduceStock(ordersku.getId(), ordersku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }

    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        //订单校验的主方法
        //if(orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <=0){
        //
        //}//利用注释校验过了
        List<Long> skuIdList = orderDTO
                .getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());
        List<Sku> skuList = skuService.getSkuByIds(skuIdList);

        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            Coupon coupon = this.couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40003));
            UserCoupon userCoupon = this.userCouponRepository
                    .findFirstByUserIdAndCouponIdAndStatus(uid, couponId, 1)
                    .orElseThrow(() -> new NotFoundException(50006));
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList
                , couponChecker, maxSkuLimit);
        orderChecker.isOK();
        return orderChecker;
    }

    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date now = new Date();
        return this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId
                (now,OrderStatus.UNPAID.value(),uid,pageable);
    }

    public Page<Order> getByStatus(Integer status,Integer page,Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Long uid =LocalUser.getUser().getId();
        if(status==OrderStatus.All.value()){
            return this.orderRepository.findByUserId(uid,pageable);
        }
        return this.orderRepository.findByUserIdAndStatus(uid,status,pageable);
    }

    public void updateOrderPrepayId(Long orderId, String prePayId) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setPrepayId(prePayId);
            this.orderRepository.save(o);
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }

    public Optional<Order> getOrderDetail(Long oid){
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findFirstByUserIdAndId(uid,oid);
    }

}
