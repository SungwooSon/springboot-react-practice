package com.hanwhalife.poc.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class NoticeResponseWithPagination {

    List<NoticeResponse> content;
    Pagination pagination;
}
