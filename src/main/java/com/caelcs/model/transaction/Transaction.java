package com.caelcs.model.transaction;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Transaction(UUID id, LocalDate creationDate, String description, Amount amount, OperationType type) {
}
