package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.dto.NoticeCreate;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.response.NoticeResponse;
import com.hanwhalife.poc.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void save(@RequestBody @Valid NoticeCreate request) {
        //request.validate();

        noticeService.save(request);
    }


    /**
     * todo : paging, sorting, filtering
     *
     * @param
     * @return
     */
    @GetMapping
    public List<NoticeResponse> getNoticeList() {
        return noticeService.getList();
    }

    @GetMapping("/{id}")
    public NoticeResponse getNotice(@PathVariable Long id) {
        return noticeService.get(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid NoticeEdit noticeEdit) {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }
}
