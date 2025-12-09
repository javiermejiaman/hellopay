package com.jm.hellopay.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class F {

    /**
     * Converts USD BigDecimal representation to Long.
     * <p>
     * Used to comply with Stripe money amounts format requirements.
     *
     * @param moneyValue the BigDecimal representation of money.
     * @return the Long representation of money.
     */
    public static Long toLong(BigDecimal moneyValue) {
        return moneyValue
                .multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_EVEN)
                .longValue();
    }

}
