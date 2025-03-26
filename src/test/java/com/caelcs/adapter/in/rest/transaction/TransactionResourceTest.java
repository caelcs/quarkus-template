package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.application.port.out.rest.transaction.GetTransactionsCase;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionResourceTest {

    @Mock
    private GetTransactionsCase getTransactionsCase;

    @InjectMocks
    private TransactionResource resource;

    @Test
    void test_getTransactionsByAccountNumberAndType_givenAValidAccountWithTransactions_thenSuccess() {
        // Given
        String accountNumber = "4455766754";
        AccountType accountType = AccountType.CREDIT;

        //And
        Transaction expectedTransaction = TransactionMother.base();
        when(getTransactionsCase.getTransactionsByAccount(eq(accountNumber), eq(accountType)))
                .thenReturn(List.of(expectedTransaction));

        // When
        TransactionsWebModel result = resource.getTransactionsByAccountNumberAndType(accountNumber, accountType);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.transactions());
        Assertions.assertFalse(result.transactions().isEmpty());
    }

}