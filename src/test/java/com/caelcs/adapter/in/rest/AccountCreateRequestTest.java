package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountCreateRequestTest {

    @Test
    void test_toAccountDTO_success() {
        //Given
        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .accountNumber("345345")
                .accountType(AccountType.DEBIT)
                .build();

        //When
        AccountDTO result = accountCreateRequest.toAccountDTO();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateRequest.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateRequest.accountType(), result.accountType());
    }

}