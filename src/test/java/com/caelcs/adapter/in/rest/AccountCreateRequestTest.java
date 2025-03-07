package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountCreateRequestTest {

    @Test
    void test_toAccountDTO_success() {
        //Given
        AccountCreateRequest accountCreateRequest = AccountCreateRequestMother.base();

        //When
        AccountDTO result = accountCreateRequest.toAccountDTO();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountCreateRequest.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountCreateRequest.accountType(), result.accountType());
    }

}