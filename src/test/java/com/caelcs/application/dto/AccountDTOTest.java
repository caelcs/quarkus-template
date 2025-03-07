package com.caelcs.application.dto;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountDTOTest {

    @Test
    void test_toAccount_success() {
        //Given
        AccountDTO accountDTO = AccountDTO.builder().accountNumber("34234234234").accountType(AccountType.DEBIT).build();

        //When
        Account result = accountDTO.toAccount();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountDTO.accountNumber(), result.accountNumber());
        Assertions.assertEquals(accountDTO.accountType(), result.accountType());
    }

}