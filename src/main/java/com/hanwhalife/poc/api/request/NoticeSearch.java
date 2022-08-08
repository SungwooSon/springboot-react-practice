package com.hanwhalife.poc.api.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeSearch {

    private int page;


    private int size;

    @Builder
    public NoticeSearch(Integer page, Integer size) {
        this.page = page == null ? 1 : page;
        this.size = size;
    }
}
