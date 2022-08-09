package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.request.DeleteIds;
import com.hanwhalife.poc.api.request.NoticeCreate;
import com.hanwhalife.poc.api.dto.NoticeSpecification;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.hanwhalife.poc.api.response.NoticeResponse;
import com.hanwhalife.poc.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
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
    public List<NoticeResponse> getNoticeList(@RequestParam(required = false) String keyword, @ModelAttribute NoticeSearch noticeSearch) {

        Specification<Notice> spec = (root, query, criteriaBuilder) -> null;

        if (StringUtils.hasText(keyword)) {
            spec = spec.and(NoticeSpecification.likeKeyword(keyword));
        }

        return noticeService.getList(spec, noticeSearch);
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
        for (Long id : ids.getIds()) {
            noticeService.delete(id);
        }
    }
}
