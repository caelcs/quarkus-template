package com.caelcs.account;

import com.caelcs.TestContainerLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
@QuarkusTestResource(TestContainerLifecycleManager.class)
public class AccountIT {

    @Test
    void testAccountCreation() {
        // Implement your integration test logic here
        // This is a placeholder for the actual test
        System.out.println("Running integration test for account creation");
    }
}
