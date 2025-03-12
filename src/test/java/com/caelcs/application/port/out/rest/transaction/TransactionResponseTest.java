package com.caelcs.application.port.out.rest.transaction;

import com.caelcs.model.transaction.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionResponseTest {

    @Test
    void test_toTransaction_GivenTransactionResponse_TheSuccess() {
        //Given
        TransactionResponse transactionResponse = TransactionResponseMother.base();

        //When
        Transaction result = TransactionResponse.toTransaction(transactionResponse);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(transactionResponse.description(), result.description());
        Assertions.assertEquals(transactionResponse.creationDate(), result.creationDate());
        Assertions.assertEquals(transactionResponse.amount(), result.amount());
        Assertions.assertEquals(transactionResponse.type(), result.type());
    }

}