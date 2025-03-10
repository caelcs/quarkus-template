package com.caelcs.adapter.out.persistence;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import com.caelcs.model.account.AccountType;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@QuarkusTest
class AccountRepositoryDefaultTest {

    @Inject
    private AccountRepositoryDefault repository;

    @Test
    @Transactional
    void test_persist_givenValidEntity_thenShouldSuccess() {
        //Given
        Account account = AccountMother.base("3444334", AccountType.DEBIT);

        //When
        repository.saveEntity(account);

        //Then
        Optional<Account> result = repository.findEntityByAccountNumberAndType(account.accountNumber(), account.accountType());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(account.id(), result.get().id());
        Assertions.assertEquals(account.accountType(), result.get().accountType());
        Assertions.assertEquals(account.accountNumber(), result.get().accountNumber());
        Assertions.assertNotNull(result.get().creationDate());
    }

    @Test
    @Transactional
    void test_findEntityByAccountNumberAndType_givenPersistedEntity_thenShouldFindItSuccess() {
        //Given
        Account account = AccountMother.base("34222222", AccountType.DEBIT);
        repository.saveEntity(account);

        //When
        Optional<Account> result = repository.findEntityByAccountNumberAndType(account.accountNumber(), account.accountType());

        //Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get().id());
        Assertions.assertEquals(account.accountType(), result.get().accountType());
        Assertions.assertEquals(account.accountNumber(), result.get().accountNumber());
        Assertions.assertNotNull(result.get().creationDate());
    }

}