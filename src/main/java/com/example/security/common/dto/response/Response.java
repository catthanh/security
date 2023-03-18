package com.example.security.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    String message;
    Object payload;
    Object meta;
    Object errors;
    String status;

    public static <T> Response<T> ok(T data) {
        return new Response<T>()
                .setMessage("Success")
                .setPayload(data)
                .setStatus("success");
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }
}
