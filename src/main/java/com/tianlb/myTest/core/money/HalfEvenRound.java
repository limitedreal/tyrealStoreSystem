package com.tianlb.myTest.core.money;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Primary
public class HalfEvenRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actual = original.multiply(discount);
        BigDecimal finalMoney = actual.setScale(2, RoundingMode.HALF_EVEN);
        //四舍五入模式
        return finalMoney;
    }
}
