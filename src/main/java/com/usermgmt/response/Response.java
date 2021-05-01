package com.usermgmt.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public @ToString class Response {

    private String message;
    private int code;

    public Response(String message) {
        this.message = message;
    }
}
