package com.caelcs.account;

import com.caelcs.adapter.in.rest.account.AccountCreateWebModel;
import com.caelcs.adapter.in.rest.account.AccountCreateWebModelMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;


@SuppressFBWarnings(value = {"M", "B", "CT"}, justification = "gatling tests")
public class AccountSimulation extends Simulation {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(System.getenv("QUARKUS_APP_URL"))
            .acceptHeader(MediaType.APPLICATION_JSON)
            .contentTypeHeader(MediaType.APPLICATION_JSON);

    private final ScenarioBuilder scn = scenario("Create Account")
            .exec(session -> session.set("accessToken", OIDCApi.getAccessToken("alice", "alice")))
            .exec(session -> session.set("accountDetail", createAccountPayload()))
            .exec(http("Create Account")
                    .post("/accounts")
                    .header(HttpHeaders.AUTHORIZATION, session -> String.format("Bearer %s", session.getString("accessToken")))
                    .body(StringBody(session -> {
                        try {
                            return toJson(session.get("accountDetail"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "";
                        }
                    }))
                    .check(status().is(201))
            ).exec(http("Get Account")
                    .post(session -> String.format("/accounts?accountNumber=%s&accountType=%s", session.<AccountCreateWebModel>get("accountDetails").accountNumber(), session.<AccountCreateWebModel>get("accountDetails").accountType().name()))
                    .header(HttpHeaders.AUTHORIZATION, session -> String.format("Bearer %s", session.getString("accessToken")))
                    .body(StringBody(session -> {
                        try {
                            return toJson(session.get("accountDetail"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "";
                        }
                    }))
                    .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(
                        constantUsersPerSec(Integer.parseInt(System.getenv("CONCURRENT_USERS")))
                                .during(Integer.parseInt(System.getenv("DURATION")))
                )
        ).protocols(httpProtocol);
    }

    private static String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    private static AccountCreateWebModel createAccountPayload() {
        return AccountCreateWebModelMother.base();
    }
}