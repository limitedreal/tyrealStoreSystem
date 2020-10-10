package com.tyreal.myTest.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class HalfRoundMoney implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actual = original.multiply(discount);
        BigDecimal finalMoney = actual.setScale(2, RoundingMode.HALF_UP);
        //四舍五入模式
        return finalMoney;
    }
}
