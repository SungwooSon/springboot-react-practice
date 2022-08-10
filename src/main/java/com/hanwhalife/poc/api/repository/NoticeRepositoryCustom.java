package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> getList(String keyword, NoticeSearch noticeSearch);

}
