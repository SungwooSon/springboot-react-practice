package com.hanwhalife.poc.api.repository;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.hanwhalife.poc.api.request.NoticeSearchCondition;
import com.hanwhalife.poc.api.response.NoticeResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

import static com.hanwhalife.poc.api.domain.QNotice.notice;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notice> getList(NoticeSearch noticeSearch) {

        BooleanBuilder builder = new BooleanBuilder();
        if(noticeSearch.getKeyword() != null) {
            builder.and(notice.title.contains(noticeSearch.getKeyword()));
        }

        return jpaQueryFactory.selectFrom(notice)
                .where(builder)
                .limit(noticeSearch.getSize())
                .offset(noticeSearch.getOffset())
                .orderBy(notice.id.desc())
                .fetch();
    }

    @Override
    public int getCount() {
        return jpaQueryFactory.selectFrom(notice)
                .fetch()
                .size();
    }

    @Override
    public Page<NoticeResponse> searchPageSimple(NoticeSearchCondition condition, Pageable pageable) {

        List<Notice> fetch = jpaQueryFactory.selectFrom(notice)
                .where(titleContains(condition.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(noticeSort(pageable))
                .fetch();

        List<NoticeResponse> collect = fetch.stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, fetch.size());
    }

    private BooleanExpression titleContains(String title) {
        return isEmpty(title) ? null : notice.title.contains(title);
    }

    private OrderSpecifier<?> noticeSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "id":
                        return new OrderSpecifier(direction, notice.id);
                    case "title":
                        return new OrderSpecifier(direction, notice.title);
                }
            }
        }
        return null;
    }


}
