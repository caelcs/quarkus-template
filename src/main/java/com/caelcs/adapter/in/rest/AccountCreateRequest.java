package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.AccountType;
import lombok.Builder;

@Builder(toBuilder = true)
public record AccountCreateRequest(String accountNumber, AccountType accountType) {
}
