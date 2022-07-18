package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.dto.NoticeCreate;
import com.hanwhalife.poc.api.exception.NoticeNotFound;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public NoticeResponse get(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoticeNotFound::new);

        return NoticeResponse.builder()
                            .id(notice.getId())
                            .title(notice.getTitle())
                            .content(notice.getContent())
                            .registerDate(notice.getRegisterDate().format(DateTimeFormatter.ISO_DATE))
                            .writer(notice.getWriter().getUsername())
                            .build();
    }

    public List<NoticeResponse> getList() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());
    }

    public void save(NoticeCreate noticeCreate) {
        //-> noticeCreate -> Notice -|<  -|0-

        Notice notice = Notice.builder()
                .title(noticeCreate.getTitle())
                .content(noticeCreate.getContent())
                .registerDate(LocalDateTime.now())
                .writer(userRepository.findById(noticeCreate.getUserId())
                        .orElseThrow(UserNotFoundException::new))
                .build();

        noticeRepository.save(notice);
    }
}
