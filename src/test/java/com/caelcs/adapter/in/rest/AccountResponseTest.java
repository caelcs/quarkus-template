package com.caelcs.adapter.in.rest;

import com.caelcs.model.account.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountResponseTest {

    @Test
    void test_fromAccount_success() {
        //Given
        Account account = Account.builder().build();

        //When
        AccountResponse result = AccountResponse.fromAccount(account);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(account.accountNumber(), result.accountNumber());
        Assertions.assertEquals(account.accountType(), result.accountType());
    }

}