package com.caelcs.application.service.account;

import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponse;
import com.caelcs.application.port.out.rest.transaction.TransactionsResponseMother;
import com.caelcs.application.service.transaction.GetTransactionsService;
import com.caelcs.model.account.AccountType;
import com.caelcs.model.transaction.Transaction;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTransactionsServiceTest {

    @Mock
    private TransactionsClient client;

    @InjectMocks
    private GetTransactionsService service;

    @Test
    void test_getTransactionsByAccount_GivenValidAccountWithTransactions_ThenSuccess() {
        //Given
        AccountType accountType = AccountType.DEBIT;
        String accountNumber = "333444";

        //And
        TransactionsResponse expectedTransactionResponse = TransactionsResponseMother.base();
        when(client.getTransactionsByAccountNumberAndType(eq(accountNumber), eq(accountType)))
                .thenReturn(RestResponse.ok(expectedTransactionResponse));

        //When
        List<Transaction> results = service.getTransactionsByAccount(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
        assertTransaction(expectedTransactionResponse, results);
    }

    @Test
    void test_getTransactionsByAccount_GivenValidAccountWithNoTransactions_ThenSuccess() {
        //Given
        AccountType accountType = AccountType.DEBIT;
        String accountNumber = "333444";

        //And
        when(client.getTransactionsByAccountNumberAndType(eq(accountNumber), eq(accountType)))
                .thenReturn(RestResponse.notFound());

        //When
        List<Transaction> results = service.getTransactionsByAccount(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(results);
        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    void test_getTransactionsByAccount_Given404ResponseFromService_ThenEmptyListSuccess() {
        //Given
        AccountType accountType = AccountType.DEBIT;
        String accountNumber = "333444";

        //And
        TransactionsResponse expectedTransactionResponse = TransactionsResponseMother.baseNoTransactions();
        when(client.getTransactionsByAccountNumberAndType(eq(accountNumber), eq(accountType)))
                .thenReturn(RestResponse.ok(expectedTransactionResponse));

        //When
        List<Transaction> results = service.getTransactionsByAccount(accountNumber, accountType);

        //Then
        Assertions.assertNotNull(results);
        Assertions.assertTrue(results.isEmpty());
    }

    private static void assertTransaction(TransactionsResponse expectedTransactionResponse, List<Transaction> results) {
        Assertions.assertEquals(expectedTransactionResponse.transactions().getFirst().description(), results.getFirst().description());
        Assertions.assertEquals(expectedTransactionResponse.transactions().getFirst().type(), results.getFirst().type());
        Assertions.assertEquals(expectedTransactionResponse.transactions().getFirst().creationDate(), results.getFirst().creationDate());
        Assertions.assertEquals(expectedTransactionResponse.transactions().getFirst().amount(), results.getFirst().amount());
    }
}