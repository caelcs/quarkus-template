package com.caelcs.adapter.out.rest.transaction;

import com.caelcs.adapter.out.rest.exception.mappers.RestClientExceptionMapper;
import com.caelcs.adapter.out.rest.MDCClientRequestFilter;
import com.caelcs.adapter.out.rest.exception.mappers.ProcessingExceptionMapper;
import com.caelcs.application.port.out.rest.transaction.TransactionsClient;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.Produces;
import lombok.AllArgsConstructor;

import java.net.URI;

@Dependent
@AllArgsConstructor
public class RestClientConfiguration {

    private TransactionsConfig config;

    @Produces
    @Default
    public TransactionsClient transactionsClient() {
        return QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(config.url()))
                .property("resteasy.connectionPoolSize", config.connectionPoolSize())
                .property("resteasy.maxConnections", config.maxConnections())
                .property("resteasy.connectionTTL", config.connectionTtl())
                .property("resteasy.connectionTimeout", config.connectionTimeout())
                .property("resteasy.idleTimeout", config.idleTimeout())
                .disableDefaultMapper(true)
                .register(MDCClientRequestFilter.class)
                .register(ProcessingExceptionMapper.class)
                .register(RestClientExceptionMapper.class)
                .build(TransactionsClient.class);
    }

}
