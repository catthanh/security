package com.example.security.common.dto.response;

public class ResponseBuilder {
    private Response response;

    public ResponseBuilder message(String message) {
        response.setMessage(message);
        return this;
    }

    public Response build() {
        return response;
    }
}
