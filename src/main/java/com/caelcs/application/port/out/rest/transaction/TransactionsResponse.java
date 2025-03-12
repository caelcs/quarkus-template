package com.caelcs.application.port.out.rest.transaction;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record TransactionsResponse(List<TransactionResponse> transactions) {
}
