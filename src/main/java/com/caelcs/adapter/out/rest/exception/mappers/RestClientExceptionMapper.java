package com.caelcs.adapter.out.rest.exception.mappers;

import com.caelcs.application.port.out.rest.common.exception.RestClientException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestClientExceptionMapper implements ExceptionMapper<RestClientException> {

    @Override
    public Response toResponse(RestClientException e) {
        return Response
                .status(e.getStatus())
                .entity(e.getMessage())
                .build();
    }

}
