package com.caelcs.adapter.in.rest.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.dto.AccountDTOMother;
import com.caelcs.application.port.in.account.CreateAccountUseCase;
import com.caelcs.application.port.in.account.GetAccountUseCase;
import com.caelcs.application.port.in.transaction.GetTransactionsCase;
import com.caelcs.model.account.Account;
import com.caelcs.model.account.AccountMother;
import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;
import com.caelcs.model.transaction.TransactionMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountResourceTest {

    @Mock
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private GetAccountUseCase getAccountUseCase;

    @Mock
    private GetTransactionsCase getTransactionsCase;

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

    @Test
    void test_getAccountByAccountNumberAndType_GivenExistingAccountWithNoTransactions_thenSuccess() {
        //Given
        String accountNumber = "343444678";
        AccountType accountType = AccountType.DEBIT;

        //And
        Account expectedAccount = AccountMother.base(accountNumber, accountType);
        when(getAccountUseCase.getAccountByAccountNumberAndType(eq(accountNumber), eq(accountType)))
                .thenReturn(Optional.of(expectedAccount));

        //And
        Transaction expectedTransaction = TransactionMother.base();
        when(getTransactionsCase.getTransactionsByAccount(accountNumber, accountType))
                .thenReturn(List.of(expectedTransaction));

        //When
        AccountResponse result = resource.getAccountByAccountNumberAndType(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedAccount.accountNumber(), result.accountNumber());
        Assertions.assertEquals(expectedAccount.accountType(), result.accountType());
        Assertions.assertNotNull(result.id());
        Assertions.assertNotNull(result.creationDate());
        Assertions.assertEquals(1, result.transactions().size());

        //And
        assertTransaction(expectedTransaction, result);
    }

    private static void assertTransaction(Transaction expectedTransaction, AccountResponse result) {
        Assertions.assertEquals(expectedTransaction.id(), result.transactions().getFirst().id());
        Assertions.assertEquals(expectedTransaction.type(), result.transactions().getFirst().type());
        Assertions.assertEquals(expectedTransaction.amount(), result.transactions().getFirst().amount());
        Assertions.assertEquals(expectedTransaction.description(), result.transactions().getFirst().description());
        Assertions.assertEquals(expectedTransaction.creationDate(), result.transactions().getFirst().creationDate());
    }
}