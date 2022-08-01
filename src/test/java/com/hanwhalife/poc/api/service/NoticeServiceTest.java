package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("auditing test")
    void test1() {
        Notice notice = Notice.builder()
                .title("제목")
                .content("내용")
                .writer(userRepository.findById(1l).get())
                .registrationDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        assertThat(notice.getId()).isNotNull();

        assertThat(notice.getCreateAt().getYear()).isEqualTo(LocalDateTime.now().getYear());
        assertThat(notice.getCreateAt().getMonth()).isEqualTo(LocalDateTime.now().getMonth());
        assertThat(notice.getCreateAt().getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }


    @Test
    void deleteTest() {
        //given
        //noticeRepository.findById(1l);

        //when
        noticeService.delete(1l);

        //then
        Assertions.assertThat(noticeRepository.count()).isEqualTo(2);
    }
}
