package me.john.amiscaray.springsecuritydemo.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionImpl extends AuthenticationException {

    public AuthenticationExceptionImpl(String msg) {
        super(msg);
    }

}
