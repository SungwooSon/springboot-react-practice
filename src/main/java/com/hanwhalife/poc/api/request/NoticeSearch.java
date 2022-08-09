package com.hanwhalife.poc.api.request;

import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.*;

@Getter
@Builder
public class NoticeSearch {


    private static final  int MAX_SIZE = 20;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long)(max(page, 1) - 1) * min(size, MAX_SIZE);
    }
}
