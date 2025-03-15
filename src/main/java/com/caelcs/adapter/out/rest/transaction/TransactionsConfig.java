package com.caelcs.adapter.out.rest.transaction;


import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus.rest-client.transactions-api")
public interface TransactionsConfig {

    String url();

}
