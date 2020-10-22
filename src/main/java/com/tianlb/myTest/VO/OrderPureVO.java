package com.tianlb.myTest.VO;

import com.tianlb.myTest.model.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
public class OrderPureVO extends Order {
    private Long period;
    private Date createTime;

    public OrderPureVO(Order order, Long period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}

