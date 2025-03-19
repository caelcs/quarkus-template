package com.caelcs.nativeTest.account;

import com.caelcs.application.dto.AccountDTO;
import com.caelcs.application.dto.AccountDTOMother;
import com.caelcs.model.account.AccountType;
import com.tngtech.jgiven.junit5.ScenarioTest;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@ConnectWireMock
@QuarkusIntegrationTest
public class AccountIT extends ScenarioTest<CreateAccountGiven, CreateAccountWhen, CreateAccountThen> {

    @Test
    void create_accounts_api() {
        AccountDTO accountDTO = AccountDTOMother.base("3434545", AccountType.DEBIT);
        given().the_bank_accounts(accountDTO);
        when().call_account_api_to_create();
        then().account_response_should_exist()
                .and().should_have_created_all_accounts();
    }
}
