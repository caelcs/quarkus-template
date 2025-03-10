package com.caelcs.adapter.in.rest;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.dto.AccountDTOMother;
import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountResourceTest {

    @Mock
    private CreateAccountUseCase createAccountUseCase;

    @InjectMocks
    private AccountResource resource;

    @Test
    void test_create_givenNonExistentAccount_thenSuccess() {
        //Given
        AccountCreateRequest request = AccountCreateRequestMother.base();

        //And
        Account expectedAccount = AccountMother.base(request.accountNumber(), request.accountType());
        AccountDTO accountDTO = AccountDTOMother.base(request.accountNumber(), request.accountType());
        when(createAccountUseCase.create(accountDTO)).thenReturn(expectedAccount);

        //When
        AccountResponse result = resource.create(request);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedAccount.accountNumber(), result.accountNumber());
        Assertions.assertEquals(expectedAccount.accountType(), result.accountType());
    }
}