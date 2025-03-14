package com.caelcs.adapter.out.rest.transaction;

import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.Produces;
import lombok.AllArgsConstructor;

import java.net.URI;

@Dependent
@AllArgsConstructor
public class RestClientConfiguration {

    private TransactionsConfig config;

    @Produces
    public TransactionsClient transactionsClient() {
        return QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(config.getUrl()))
                .build(TransactionsClient.class);
    }

}
