package com.caelcs.nativeTest.account;

import com.caelcs.application.dto.AccountDTO;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Table;

import java.util.Arrays;

class CreateAccountGiven extends Stage<CreateAccountGiven> {

    @ProvidedScenarioState
    private SharedAccountState sharedAccountState = new SharedAccountState();

    CreateAccountGiven the_bank_accounts(@Table AccountDTO...accountsDTO) {
        sharedAccountState.setAccounts(Arrays.asList(accountsDTO));
        return self();
    }
}
