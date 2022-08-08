package com.hanwhalife.poc.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanwhalife.poc.api.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String registrationDate;

    @Builder
    public NoticeResponse(Long id, String title, String content, String writer, String registrationDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.registrationDate = registrationDate;
    }

    public NoticeResponse(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = notice.getWriter().getUsername();
        this.registrationDate = notice.getRegistrationDate().format(DateTimeFormatter.ISO_DATE);
    }
}
