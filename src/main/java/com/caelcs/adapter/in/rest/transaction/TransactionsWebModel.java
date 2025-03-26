package com.caelcs.adapter.in.rest.transaction;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
@SuppressFBWarnings(value = {"EI2", "EI"}, justification = "This list is safely managed elsewhere")
public record TransactionsWebModel(List<TransactionWebModel> transactions) {
}
