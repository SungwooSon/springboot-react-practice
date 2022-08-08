package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> getList(NoticeSearch noticeSearch);

}
