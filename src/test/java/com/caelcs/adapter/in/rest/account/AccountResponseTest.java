package com.caelcs.adapter.in.rest.account;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountResponseTest {

    @Test
    void test_fromAccount_success() {
        //Given
        Account account = AccountMother.base();

        //When
        AccountResponse result = AccountResponse.fromAccount(account);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(account.accountNumber(), result.accountNumber());
        Assertions.assertEquals(account.accountType(), result.accountType());
    }

}