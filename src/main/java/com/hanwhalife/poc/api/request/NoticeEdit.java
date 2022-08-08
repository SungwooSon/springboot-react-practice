package com.hanwhalife.poc.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NoticeEdit {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    //@NotBlank(message = "내용을 입력해주세요")
    private String content;

    @Builder
    public NoticeEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //private Long userId;

}
