package com.tianlb.myTest.core.money;

import java.math.BigDecimal;

public interface IMoneyDiscount {
    BigDecimal discount(BigDecimal original,BigDecimal discount);
    
}
