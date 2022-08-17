package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.*;
import com.hanwhalife.poc.api.dto.NoticeSpecification;
import com.hanwhalife.poc.api.response.NoticeResponse;
import com.hanwhalife.poc.api.response.NoticeResponseWithPagination;
import com.hanwhalife.poc.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public NoticeResponse save(@RequestBody @Valid NoticeCreate request) {
        //request.validate();

        return noticeService.save(request);
    }


    /**
     * todo : paging, sorting, filtering(title)
     *
     * @param
     * @return
     */
    @GetMapping
   // public List<NoticeResponse> getNoticeList(@RequestParam(required = false) String keyword, @ModelAttribute NoticeSearch noticeSearch) {
    public NoticeResponseWithPagination getNoticeList(@ModelAttribute NoticeSearch noticeSearch) {


        log.info("keyword={}", noticeSearch.getKeyword());

        Specification<Notice> spec = (root, query, criteriaBuilder) -> null;

        if (StringUtils.hasText(noticeSearch.getKeyword())) {
            spec = spec.and(NoticeSpecification.likeKeyword(noticeSearch.getKeyword()));
        }

        return noticeService.getList(noticeSearch);
    }

    @GetMapping("/test")
    public Page<NoticeResponse> getNoticeList2(
            @ModelAttribute NoticeSearchCondition condition,
            @PageableDefault(size=10, page=0, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("pageable={}", pageable);

        Page<NoticeResponse> listWithPageable = noticeService.getListWithPageable(condition, pageable);

        List<NoticeResponse> content = listWithPageable.getContent();
        int totalPages = listWithPageable.getTotalPages();
        int size = listWithPageable.getSize();
        listWithPageable.getTotalElements();

        //total
        //limit
        //currentPage
        return listWithPageable;
    }

    @GetMapping("/{id}")
    public NoticeResponse getNotice(@PathVariable Long id) {
        return noticeService.get(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid NoticeEdit noticeEdit) {
        noticeService.edit(id, noticeEdit);
    }

    @DeleteMapping
    //public void delete(@PathVariable Long id) {
    public void delete(@RequestBody DeleteIds ids) {
        //한방 쿼리로 리팩토링
        for (Long id : ids.getIds()) {
            noticeService.delete(id);
        }
    }
}
