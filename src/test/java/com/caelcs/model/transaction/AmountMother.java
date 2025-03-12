package com.caelcs.model.transaction;

import java.math.BigDecimal;

public class AmountMother {

    public static Amount base() {
        return Amount.builder()
                .currency("USD")
                .amount(BigDecimal.TEN)
                .build();
    }

}
