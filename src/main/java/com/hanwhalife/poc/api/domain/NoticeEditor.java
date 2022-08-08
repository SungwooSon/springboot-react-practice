package com.hanwhalife.poc.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeEditor {

    private final String title;
    private final String content;

    @Builder
    public NoticeEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
