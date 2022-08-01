package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.dto.NoticeCreate;
import com.hanwhalife.poc.api.exception.NoticeNotFound;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<NoticeResponse> getList(Specification<Notice> spec) {
        return noticeRepository.findAll(spec).stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());
    }

    public void save(NoticeCreate noticeCreate) {
        //-> noticeCreate -> Notice -|<  -|0-

        Notice notice = Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .registrationDate(LocalDateTime.now())
                .writer(userRepository.findById(noticeCreate.getUserId())
                        .orElseThrow(UserNotFoundException::new))
                .build();

        noticeRepository.save(notice);
    }

    public void edit(Long id, NoticeEdit postEdit) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        //PostEditor po

        //notice.edit();

        noticeRepository.save(notice);
    }


    public void delete(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        noticeRepository.delete(notice);

    }
}
