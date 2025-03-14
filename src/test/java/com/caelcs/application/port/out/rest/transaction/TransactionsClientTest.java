package com.caelcs.application.port.out.rest.transaction;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionsClientTest {

    @Inject
    @RestClient
    private TransactionsClient client;

    @Test
    void test_getTransactionsByAccountNumberAndType_GivenValidAccount_thenListAllTransactionsSuccess() {

    }

}