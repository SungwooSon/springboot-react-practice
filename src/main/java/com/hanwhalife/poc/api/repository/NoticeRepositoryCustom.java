package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.hanwhalife.poc.api.request.NoticeSearchCondition;
import com.hanwhalife.poc.api.response.NoticeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> getList(NoticeSearch noticeSearch);

    public int getCount();

    Page<NoticeResponse> searchPageSimple(NoticeSearchCondition condition, Pageable pageable);

}
