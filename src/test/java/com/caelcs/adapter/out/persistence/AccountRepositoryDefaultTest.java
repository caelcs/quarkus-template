package com.caelcs.adapter.out.persistence;

import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AccountRepositoryDefaultTest {

    @Inject
    private AccountRepositoryDefault repository;

    @Test
    @Transactional
    void test_persist_givenValidEntity_thenShouldSuccess() {
        //Given
        Account account = AccountMother.base();

        //When
        repository.saveEntity(account);

        //Then
        PanacheQuery<AccountEntity> all = repository.findAll();
        Assertions.assertEquals(1, all.count());
        Assertions.assertEquals(account.id(), all.firstResult().getId());
        Assertions.assertEquals(account.accountType(), all.firstResult().getAccountType());
        Assertions.assertEquals(account.accountNumber(), all.firstResult().getAccountNumber());
        Assertions.assertNotNull(all.firstResult().getCreationDate());
    }
}