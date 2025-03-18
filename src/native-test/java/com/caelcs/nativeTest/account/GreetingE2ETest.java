package com.caelcs.nativeTest.account;

import com.tngtech.jgiven.junit5.ScenarioTest;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class GreetingE2ETest extends ScenarioTest<GreetingGiven, GreetingWhen, GreetingThen> {

    @Test
    void should_return_correct_greeting() {
        given().a_quarkus_service_running();
        when().I_request_the_greeting();
        then().setResponse(when().response()).the_response_should_be("Hello world");
    }
}
