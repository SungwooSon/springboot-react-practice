package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.domain.NoticeEditor;
import com.hanwhalife.poc.api.exception.NoticeNotFound;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.NoticeCreate;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.hanwhalife.poc.api.request.NoticeSearchCondition;
import com.hanwhalife.poc.api.response.NoticeResponse;
import com.hanwhalife.poc.api.response.NoticeResponseWithPagination;
import com.hanwhalife.poc.api.response.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    public NoticeResponse get(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        log.info("notice={}", notice);

        return NoticeResponse.builder()
                            .id(notice.getId())
                            .title(notice.getTitle())
                            .content(notice.getContent())
                            .registrationDate(notice.getRegistrationDate().format(DateTimeFormatter.ISO_DATE))
                            .writer(notice.getWriter().getUsername())
                            .build();
    }

    @Transactional
    public NoticeResponseWithPagination getList(NoticeSearch noticeSearch) {
        /*return noticeRepository.findAll(spec).stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());*/

        List<NoticeResponse> content = noticeRepository.getList(noticeSearch).stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());

        int totalCount = noticeRepository.getCount();

        Pagination pagination = Pagination.builder()
                .totalCount(totalCount)
                .currentPage(noticeSearch.getPage())
                .limit(noticeSearch.getSize())
                .build();

        return NoticeResponseWithPagination.builder()
                .content(content)
                .pagination(pagination)
                .build();
    }
    @Transactional
    public Page<NoticeResponse> getListWithPageable(NoticeSearchCondition condition, Pageable pageable) {
        return noticeRepository.searchPageSimple(condition, pageable);
    }

    public NoticeResponse save(NoticeCreate noticeCreate) {
        //-> noticeCreate -> Notice -|<  -|0-

        Notice notice = Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .registrationDate(LocalDateTime.now())
                .writer(userRepository.findById(noticeCreate.getUserId())
                        .orElseThrow(UserNotFoundException::new))
                .build();

        noticeRepository.save(notice);

        return NoticeResponse.builder()
                .id(notice.getId())
                .build();
    }


    @Transactional
    public void edit(Long id, NoticeEdit noticeEdit) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        //Notice 에 setter 달기 껄끄러움.
        //notice.setTitle(noticeEdit.getTitle());
        //notice.setContent(noticeEdit.getContent());

        //parameter 로 넘기는 방식이 순서가 바뀌는 경우 버그를 발견하기 힘들고, 전달인자 타입 변환, 숫자 증가 등 이슈에 취약하다.
        //notice.change(noticeEdit.getTitle(), noticeEdit.getContent());



        NoticeEditor.NoticeEditorBuilder editorBuilder = notice.toEditor();

        /* 프론트에서 수정할 데이터만 넘길경우.
        if(noticeEdit.getTitle() != null) {
            editorBuilder.title(noticeEdit.getTitle());
        }

        if(noticeEdit.getContent() != null) {
            editorBuilder.content(noticeEdit.getContent());
        }

        NoticeEditor noticeEditor = editorBuilder.build();
        */

        NoticeEditor noticeEditor = editorBuilder
                .title(noticeEdit.getTitle())
                .content(noticeEdit.getContent())
                .build();

        //전달인자를 하나만 넘기고
        //NoticeEditor에 있는 필드만 수정가능함을 코드로 알수 있음.
        notice.edit(noticeEditor);

        //noticeRepository.save(notice);
    }


    public void delete(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        List<Long> ids = new ArrayList<>();

        noticeRepository.delete(notice);

    }
}
