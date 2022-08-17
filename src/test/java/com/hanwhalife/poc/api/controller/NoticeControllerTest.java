package com.hanwhalife.poc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.DeleteIds;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.service.NoticeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class NoticeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Autowired NoticeRepository noticeRepository;
    @Autowired
    NoticeService noticeService;

    @Test
    @DisplayName("게시글 리스트 조회")
    void noticeList() throws Exception {

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

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/notices?page=1&size=20")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.length()", Matchers.is(20)))
                .andExpect(jsonPath("$.content[0].title").value("foo29"))
                .andExpect(jsonPath("$.content[0].content").value("bar29"))
                .andDo(print());
    }
    @Test
    @DisplayName("게시글 리스트 조회- 페이지 0으로 조회")
    void noticeList2() throws Exception {

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

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/notices?page=0&size=20")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content.length()", Matchers.is(20)))
                .andExpect(jsonPath("$.content.[0].title").value("foo29"))
                .andExpect(jsonPath("$.content.[0].content").value("bar29"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteNotice() throws Exception {

        Notice notice = Notice.builder()
                .title("제목")
                .content("내용")
                .writer(userRepository.findById(1l).get())
                .registrationDate(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        DeleteIds request = new DeleteIds(Arrays.asList(notice.getId()));
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                delete("/api/notices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }


    @Test
    void noticeEditTest() throws Exception {
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

        mockMvc.perform(
                        patch("/api/notices/{noticeId}", notice.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(noticeEdit))

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
