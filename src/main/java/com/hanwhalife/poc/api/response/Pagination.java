package com.hanwhalife.poc.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pagination {
    private int totalCount;  // 전체 포스트 갯수
    private long limit;      // 현재 페이지에 보일 포스트 갯수
    private int currentPage; // 현재 페이지

}
