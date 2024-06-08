package com.uai.lppa.security.controller.request;

import lombok.Value;

@Value
public class AuthenticationRequest {
    String username;
    String password;
}
