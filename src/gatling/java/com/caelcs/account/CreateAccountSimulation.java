package com.caelcs.account;

import com.caelcs.adapter.in.rest.account.AccountCreateWebModel;
import com.caelcs.adapter.in.rest.account.AccountCreateWebModelMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CreateAccountSimulation extends Simulation {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    private static AccountCreateWebModel createAccountPayload() throws Exception {
        return AccountCreateWebModelMother.base();
    }

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081") // Change to your API base URL
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private final ScenarioBuilder scn = scenario("Create Account")
            .exec(http("Create Account")
                    .post("/accounts")
                    .header("Authorization", String.format("Bearer %s", OIDCApi.getAccessToken("alice", "alice")))
                    .body(StringBody(session -> {
                        try {
                            return toJson(createAccountPayload());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "";
                        }
                    }))
                    .check(status().is(201))
            );

    {
        setUp(
                scn.injectOpen(
                        rampUsers(50).during(30) // 50 users over 30 seconds
                )
        ).protocols(httpProtocol);
    }
}