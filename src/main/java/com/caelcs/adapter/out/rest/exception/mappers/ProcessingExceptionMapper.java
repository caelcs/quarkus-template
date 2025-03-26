package com.caelcs.adapter.out.rest.exception.mappers;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Override
    public Response toResponse(ProcessingException e) {
        return Response.serverError()
                .entity("Fatal error trying to get transactions")
                .build();
    }

}
