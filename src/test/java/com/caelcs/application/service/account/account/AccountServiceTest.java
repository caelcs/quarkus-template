package com.caelcs.application.service.account.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService service;

    @Test
    void test_create_givenValidDTO_thenSuccess() {
        //Given
        AccountDTO accountDTO = AccountDTO.builder().accountNumber("232323").accountType(AccountType.DEBIT).build();

        //When
        Account result = service.create(accountDTO);
    }

}