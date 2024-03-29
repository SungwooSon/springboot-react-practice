package com.hanwhalife.poc.api.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
public class NoticeCreate {
    @NotEmpty
    private String title;
    private String content;
    private Long userId;
}
