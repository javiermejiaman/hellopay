package com.jm.hellopay.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class F {

    public static Long toLong(BigDecimal moneyValue) {
        return moneyValue
                .multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_EVEN)
                .longValue();
    }

}
