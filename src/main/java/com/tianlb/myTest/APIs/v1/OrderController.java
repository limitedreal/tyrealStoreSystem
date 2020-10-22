package com.tianlb.myTest.APIs.v1;


import com.tianlb.myTest.DTO.OrderDTO;
import com.tianlb.myTest.VO.OrderIdVO;
import com.tianlb.myTest.core.LocalUser;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.logic.OrderChecker;
import com.tianlb.myTest.service.OrderService;
import com.tianlb.myTest.BO.PageCounter;
import com.tianlb.myTest.VO.OrderPureVO;
import com.tianlb.myTest.VO.OrderSimplifyVO;
import com.tianlb.myTest.VO.PagingDozer;
import com.tianlb.myTest.core.enumeration.OrderStatus;
import com.tianlb.myTest.core.interceptors.ScopeLevel;
import com.tianlb.myTest.exception.http.ParameterException;
import com.tianlb.myTest.model.Order;
import com.tianlb.myTest.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${myTest.order.pay-time-limit}")
    private Integer payTimeLimit;

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        //orderChecker
        //couponChecker
        OrderChecker orderChecker = this.orderService.isOk(uid, orderDTO);
        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    //@SuppressWarnings("unchecked")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0")
                                         Integer start,
                                 @RequestParam(defaultValue = "10")
                                         Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems()
                .forEach((o) -> ((OrderSimplifyVO) o)
                        .setPeriod(this.payTimeLimit.longValue()));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    public PagingDozer getByStatus(@PathVariable int status,
                                   @RequestParam(name = "start", defaultValue = "0")
                                           Integer start,
                                   @RequestParam(name = "count", defaultValue = "10")
                                           Integer count) {
        //负责all paid delivered finished的查询，all传0
        if(status== OrderStatus.UNPAID.value()||status== OrderStatus.CANCELED.value()){
            throw new ParameterException(50020);
        }
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> paging = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(paging, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(this.payTimeLimit.longValue()));
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(oid);
        return orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit.longValue()))
                .orElseThrow(() -> new NotFoundException(50009));
    }

}
