package com.caelcs.adapter.out.rest.transaction;


import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.rest-client.transactions-api")
public interface TransactionsConfig {

    String url();
    Long connectionPoolSize();
    Long maxConnections();
    Long connectionTtl();
    Long connectionTimeout();
    Long idleTimeout();

}
