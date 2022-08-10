package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.request.NoticeSearch;
import com.hanwhalife.poc.api.response.NoticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @DisplayName("페이징 처리된 목록 조회")
    public void test2() {

        User user = userRepository.findById(1l).get();

        List<Notice> requestNotices = IntStream.range(0, 30)
                        .mapToObj(i -> Notice.builder()
                                .title("foo"+i)
                                .content("bar"+i)
                                .writer(user)
                                .registrationDate(LocalDateTime.now())
                                .build())
                        .collect(Collectors.toList());
        noticeRepository.saveAll(requestNotices);

        //PageRequest pageRequest = PageRequest.of(0, 5);
        NoticeSearch noticeSearch = NoticeSearch.builder()
                //.size(10)
                .page(1)
                .build();


        List<NoticeResponse> notices = noticeService.getList(null, null, noticeSearch);

        assertThat(notices.size()).isEqualTo(20);

    }


    @Test
    void edit() {
        Notice notice = Notice.builder()
                .title("제목")
                .content("내용")
                .writer(userRepository.findById(1l).get())
                .registrationDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);


        NoticeEdit noticeEdit = NoticeEdit.builder()
                .title("공지사항 입니다.")
                .content("아이러브유")
                .build();

        noticeService.edit(notice.getId(), noticeEdit);

        //then
        Notice changedNotice = noticeRepository.findById(notice.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않흡니다."));


        assertThat(changedNotice.getTitle()).isEqualTo("공지사항 입니다.");

    }

    @Test
    void edit2() {
        Notice notice = Notice.builder()
                .title("제목")
                .content("내용")
                .writer(userRepository.findById(1l).get())
                .registrationDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);


        NoticeEdit noticeEdit = NoticeEdit.builder()
                .title("공지사항 입니다.")
                //.content("아이러브유")
                .build();

        noticeService.edit(notice.getId(), noticeEdit);

        //then
        Notice changedNotice = noticeRepository.findById(notice.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재 하지 않흡니다."));


        assertThat(changedNotice.getTitle()).isEqualTo("공지사항 입니다.");
        assertThat(changedNotice.getContent()).isNull();
        //프론트 엔드와 협의봐야함. 수정되지 않을 데이터도 다 넘겨 받을지
    }
}
