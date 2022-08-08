package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanwhalife.poc.api.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notice> getList(NoticeSearch noticeSearch) {
        return jpaQueryFactory.selectFrom(notice)
                .limit(noticeSearch.getSize())
                .offset((long)(noticeSearch.getPage() - 1) * noticeSearch.getSize())
                .orderBy(notice.id.desc())
                .fetch();
    }
}
