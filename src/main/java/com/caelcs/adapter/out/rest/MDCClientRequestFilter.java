package com.caelcs.adapter.out.rest;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.MDC;

import java.util.Optional;

@Provider
public class MDCClientRequestFilter implements ClientRequestFilter {

    public static final String X_CORRELATION_ID = "X-Correlation-Id";
    public static final String CORRELATION_ID = "correlationId";

    @Override
    public void filter(ClientRequestContext clientRequestContext) {
        Optional.ofNullable(MDC.get(CORRELATION_ID))
                .ifPresent((correlationId) -> clientRequestContext.getHeaders().add(X_CORRELATION_ID, correlationId));
    }
}
