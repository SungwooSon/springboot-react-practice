package com.hanwhalife.poc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.NoticeEdit;
import com.hanwhalife.poc.api.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @DisplayName("게시글 삭제")
    void deleteNotice() throws Exception {
        mockMvc.perform(
                delete("/api/notices/{noticeId}", 1l)
                    .contentType(MediaType.APPLICATION_JSON)
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
