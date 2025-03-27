package com.caelcs.application.port.out.rest.common;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class RestClientException extends RuntimeException {

    private Response.StatusType statusCode;

    public RestClientException(Response.StatusType status) {
        super(String.format("Error has occurred, StatusCode: %s", status));
        statusCode = status;
    }

}
