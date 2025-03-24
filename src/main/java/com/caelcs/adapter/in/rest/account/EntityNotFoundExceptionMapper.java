package com.caelcs.adapter.in.rest.account;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage()).build();
    }

}
