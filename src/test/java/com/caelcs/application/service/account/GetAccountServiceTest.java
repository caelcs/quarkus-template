package com.caelcs.application.service.account;

import com.caelcs.application.port.out.persistence.account.AccountEntity;
import com.caelcs.application.port.out.persistence.account.AccountEntityMother;
import com.caelcs.application.port.out.persistence.account.AccountRepository;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private GetAccountService service;

    @Test
    void test_getAccountByAccountNumberAndType_givenExitingAccount_thenSuccess() {
        //Given
        String accountNumber = "23423424343";
        AccountType accountType = AccountType.DEBIT;

        //And
        AccountEntity expectedAccountEntity = AccountEntityMother.base(accountNumber, accountType);
        when(accountRepository.findEntityByAccountNumberAndType(eq(accountNumber), eq(accountType))).thenReturn(Optional.of(expectedAccountEntity));

        //When
        Optional<Account> result = service.getAccountByAccountNumberAndType(accountNumber, accountType);

        //Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedAccountEntity.getId(), result.get().id());
        Assertions.assertEquals(expectedAccountEntity.getAccountType(), result.get().accountType());
        Assertions.assertEquals(expectedAccountEntity.getAccountNumber(), result.get().accountNumber());
        Assertions.assertEquals(expectedAccountEntity.getCreationDate(), result.get().creationDate());
    }

    @Test
    void test_getAccountByAccountNumberAndType_givenNoExitingAccount_thenFailed() {
        //Given
        String accountNumber = "23423424343";
        AccountType accountType = AccountType.DEBIT;

        //And
        when(accountRepository.findEntityByAccountNumberAndType(eq(accountNumber), eq(accountType))).thenReturn(Optional.empty());

        //When
        Optional<Account> result = service.getAccountByAccountNumberAndType(accountNumber, accountType);

        //Then
        Assertions.assertTrue(result.isEmpty());
    }
}