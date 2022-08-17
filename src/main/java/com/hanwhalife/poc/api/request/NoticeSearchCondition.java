package com.hanwhalife.poc.api.request;

import lombok.Getter;

@Getter
public class NoticeSearchCondition {

    private String title;

    public NoticeSearchCondition(String title) {
        this.title = title;
    }
}
