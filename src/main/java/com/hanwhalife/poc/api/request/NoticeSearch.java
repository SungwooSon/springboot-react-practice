package com.hanwhalife.poc.api.request;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import static com.hanwhalife.poc.api.domain.QNotice.notice;
import static java.lang.Math.*;

@Getter
@Builder
public class NoticeSearch {

    private static final  int MAX_SIZE = 20;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 20;

    private String keyword;

    public long getOffset() {
        return (long)(max(page, 1) - 1) * min(size, MAX_SIZE);
    }
}
