package com.caelcs.nativeTest.account;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.junit.jupiter.api.Assertions;

class CreateAccountThen extends Stage<CreateAccountThen> {

    @ExpectedScenarioState
    private SharedAccountState sharedAccountState;

    CreateAccountThen account_response_should_exist() {
        Assertions.assertNotNull(sharedAccountState.getResponse());
        return self();
    }

    CreateAccountThen should_have_created_all_accounts() {
        Assertions.assertEquals(sharedAccountState.getAccounts().size(), sharedAccountState.getResponse().size());
        return self();
    }
}
