package com.caelcs.adapter.in.rest.transaction;

import com.caelcs.model.transaction.Transaction;
import com.caelcs.model.transaction.TransactionMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionWebModelTest {

    @Test
    void test_fromTransaction_GivenValidTransaction_thenSuccess() {
        //Given
        Transaction transaction = TransactionMother.base();

        //When
        TransactionWebModel result = TransactionWebModel.fromTransaction(transaction);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(transaction.amount(), result.amount());
        Assertions.assertEquals(transaction.description(), result.description());
        Assertions.assertEquals(transaction.type(), result.type());
        Assertions.assertEquals(transaction.creationDate(), result.creationDate());
    }

}