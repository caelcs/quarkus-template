package com.caelcs.adapter.out.persistence;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountEntityTest {

    @Test
    void test_fromAccount_givenValidEntity_thenSuccess() {
        //Given
        Account account = AccountMother.base();

        //When
        AccountEntity result = AccountEntity.fromAccount(account);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(account.id(), result.getId());
        Assertions.assertEquals(account.accountNumber(), result.getAccountNumber());
        Assertions.assertEquals(account.accountType(), result.getAccountType());
        Assertions.assertEquals(account.creationDate(), result.getCreationDate());
    }

    @Test
    void test_toAccount_givenValidEntity_thenSuccess() {
        //Given
        AccountEntity accountEntity = AccountEntityMother.base();

        //When
        Account result = AccountEntity.toAccount(accountEntity);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(accountEntity.getId(), result.id());
        Assertions.assertEquals(accountEntity.getAccountNumber(), result.accountNumber());
        Assertions.assertEquals(accountEntity.getAccountType(), result.accountType());
        Assertions.assertEquals(accountEntity.getCreationDate(), result.creationDate());
    }

}