package com.tyreal.myTest.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    @DecimalMin(value="0.00", message = "金额不在合法范围内" )
    @DecimalMax(value="99999999999.99", message = "金额不在合法范围内")
    private BigDecimal totalPrice;
    // 此字段仍然要传，用以确认价格是否有变化，有变化则要拒绝订单

    @DecimalMin(value="0.00", message = "金额不在合法范围内" )
    @DecimalMax(value="99999999999.99", message = "金额不在合法范围内")
    private BigDecimal finalTotalPrice;
    // 同理，如果前端算得有问题，就要拒绝订单

    private Long couponId;

    private List<SkuInfoDTO> skuInfoList;

    private OrderAddressDTO address;
}
