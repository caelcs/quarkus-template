package com.caelcs.model.transaction;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record Amount(String currency, BigDecimal amount) {
}
