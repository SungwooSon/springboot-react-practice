package com.hanwhalife.poc.api.exception;

public class NoticeNotFound extends ApiException{

    private static final String message = "존재하지 않는 공지사항입니다.";


    public NoticeNotFound() {
        super(message);
    }

    public NoticeNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
