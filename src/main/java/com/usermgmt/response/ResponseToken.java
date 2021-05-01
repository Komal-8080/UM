package com.usermgmt.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public @ToString class ResponseToken {

    private String statusMessage;
    private int statusCode;
    private String token;
}
