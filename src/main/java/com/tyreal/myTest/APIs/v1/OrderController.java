package com.tyreal.myTest.APIs.v1;


import com.tyreal.myTest.DTO.OrderDTO;
import com.tyreal.myTest.VO.OrderIdVO;
import com.tyreal.myTest.core.LocalUser;
import com.tyreal.myTest.core.interceptors.ScopeLevel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Validated
public class OrderController {

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();
        //orderChecker
        //couponChecker
        return null;
    }

}
