package com.hanwhalife.poc.api.exception;

public class UserNotFoundException extends ApiException {

    private static final String message = "존재하지 않는 사용자입니다";


    public UserNotFoundException() {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
