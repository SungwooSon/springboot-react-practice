package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.hanwhalife.poc.api.domain.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notice> getList(String keyword, NoticeSearch noticeSearch) {

        BooleanBuilder builder = new BooleanBuilder();
        if(keyword != null) {
            builder.and(notice.title.contains(keyword));
        }

        return jpaQueryFactory.selectFrom(notice)
                .where(builder)
                .limit(noticeSearch.getSize())
                .offset(noticeSearch.getOffset())
                .orderBy(notice.id.desc())
                .fetch();
    }
}
