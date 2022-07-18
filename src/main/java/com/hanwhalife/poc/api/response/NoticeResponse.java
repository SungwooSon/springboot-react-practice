package com.hanwhalife.poc.api.response;

import com.hanwhalife.poc.api.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@Getter
public class NoticeResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String registerDate;

    @Builder
    public NoticeResponse(Long id, String title, String content, String writer, String registerDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.registerDate = registerDate;
    }

    public NoticeResponse(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = notice.getWriter().getUsername();
        this.registerDate = notice.getRegisterDate().format(DateTimeFormatter.ISO_DATE);
    }
}
