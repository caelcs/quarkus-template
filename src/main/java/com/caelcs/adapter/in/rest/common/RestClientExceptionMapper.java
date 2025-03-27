package com.caelcs.adapter.in.rest.common;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestClientExceptionMapper implements ExceptionMapper<RestClientException> {

    @Override
    public Response toResponse(RestClientException e) {
        return Response
                .status(e.getStatusCode())
                .entity(e.getMessage())
                .build();
    }

}
