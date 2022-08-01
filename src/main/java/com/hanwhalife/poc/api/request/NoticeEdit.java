package com.hanwhalife.poc.api.request;

import javax.validation.constraints.NotBlank;

public class NoticeEdit {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String content;

    private Long userId;

}
