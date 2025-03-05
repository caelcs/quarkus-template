package com.caelcs.model.account;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Account(UUID id, String accountNumber, AccountType accountType, LocalDate creationDate) {
}
