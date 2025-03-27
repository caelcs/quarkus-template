package com.caelcs.application.port.out.rest.common;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class RestClientException extends RuntimeException {

    private Response.Status status;

    public RestClientException(Response.Status status) {
        super(String.format("Error has occurred, StatusCode: %s", status.getStatusCode()));
        this.status = status;
    }

}
