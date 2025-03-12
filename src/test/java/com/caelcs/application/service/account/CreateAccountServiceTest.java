package com.caelcs.application.service.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.port.out.persistence.account.AccountEntity;
import com.caelcs.application.port.out.persistence.account.AccountEntityMother;
import com.caelcs.application.port.out.persistence.account.AccountRepository;
import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionsClient transactionsClient;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @InjectMocks
    CreateAccountService service;

    @Test
    void test_create_givenValidDTO_thenSuccess() {
        //Given
        AccountDTO accountDTO = AccountDTO.builder().accountNumber("232323").accountType(AccountType.DEBIT).build();

        //And
        doNothing().when(accountRepository).saveEntity(any());

        //And
        AccountEntity expectedAccountEntity = AccountEntityMother.base();
        when(accountRepository.findEntityByAccountNumberAndType(eq(accountDTO.accountNumber()), eq(accountDTO.accountType())))
                .thenReturn(Optional.of(expectedAccountEntity));

        //When
        Account result = service.create(accountDTO);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.id());
        Assertions.assertEquals(expectedAccountEntity.getAccountNumber(), result.accountNumber());
        Assertions.assertEquals(expectedAccountEntity.getAccountType(), result.accountType());
        Assertions.assertNotNull(result.creationDate());

        //And
        verify(accountRepository).saveEntity(accountCaptor.capture());
        Account value = accountCaptor.getValue();
        Assertions.assertEquals(accountDTO.accountNumber(), value.accountNumber());
        Assertions.assertEquals(accountDTO.accountType(), value.accountType());
    }

    @Test
    void test_create_givenSaveNotThere_thenFailed() {
        //Given
        AccountDTO accountDTO = AccountDTO.builder().accountNumber("232323").accountType(AccountType.DEBIT).build();

        //And
        doNothing().when(accountRepository).saveEntity(any());

        //And
        when(accountRepository.findEntityByAccountNumberAndType(eq(accountDTO.accountNumber()), eq(accountDTO.accountType())))
                .thenReturn(Optional.empty());

        //When
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.create(accountDTO));

        //Then
        verify(accountRepository).saveEntity(accountCaptor.capture());
        Account value = accountCaptor.getValue();
        Assertions.assertEquals(accountDTO.accountNumber(), value.accountNumber());
        Assertions.assertEquals(accountDTO.accountType(), value.accountType());
    }

}