package com.caelcs.adapter.out.rest.transaction;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TransactionsConfigTest {

    @Inject
    private TransactionsConfig transactionsConfig;

    @Test
    void test_config_binding_success() {
        //Then
        Assertions.assertEquals(20L, transactionsConfig.connectionPoolSize());
        Assertions.assertEquals(50L, transactionsConfig.maxConnections());
        Assertions.assertEquals(60L, transactionsConfig.connectionTtl());
        Assertions.assertEquals(1L, transactionsConfig.connectionTimeout());
        Assertions.assertEquals(30L, transactionsConfig.idleTimeout());
    }
}