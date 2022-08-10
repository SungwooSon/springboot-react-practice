package com.hanwhalife.poc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.repository.NoticeRepository;
import com.hanwhalife.poc.api.repository.UserRepository;
import com.hanwhalife.poc.api.request.DeleteIds;
import com.hanwhalife.poc.api.request.NoticeCreate;
import com.hanwhalife.poc.api.request.NoticeEdit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "49.50.161.209", uriPort = 8080)
@ExtendWith(RestDocumentationExtension.class)
public class NoticeControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("공지 작성")
    void createNotice() throws Exception {
        NoticeCreate request = NoticeCreate.builder()
                .title("제목")
                .content("내용")
                .userId(1l)
                .build();


        String body = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/notices")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("notice-create",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(key("constraints").value("'바보'가 포함 될 수 없음")),
                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                PayloadDocumentation.fieldWithPath("userId").description("작성자 ID")
                        )
                ));
    }

    @Test
    @DisplayName("공지 단건 조회")
    void findOneNotice() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/notices/{id}", 1l)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("notice-inquiry",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("공지사항 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("공지사항 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                PayloadDocumentation.fieldWithPath("writer").description("작성자 ID"),
                                PayloadDocumentation.fieldWithPath("registrationDate").description("작성일자")
                        )

                ));
    }

    @Test
    @DisplayName("공지 리스트 조회")
    void findAllNotice() throws Exception {

        User user = userRepository.findById(1l).get();

        List<Notice> requestNotices = IntStream.range(0, 3)
                .mapToObj(i -> Notice.builder()
                        .title("title"+i)
                        .content("bar"+i)
                        .writer(user)
                        .registrationDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        noticeRepository.saveAll(requestNotices);

        FieldDescriptor[] notices = new FieldDescriptor[] {
                PayloadDocumentation.fieldWithPath("id").description("공지사항 ID"),
                PayloadDocumentation.fieldWithPath("title").description("제목"),
                PayloadDocumentation.fieldWithPath("content").description("내용"),
                PayloadDocumentation.fieldWithPath("writer").description("작성자 ID"),
                PayloadDocumentation.fieldWithPath("registrationDate").description("작성일자")
        };

        //String body = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/notices?keyword=title&page=1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("notice-list-inquiry",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("keyword").description("공지사항 제목의 일부분").optional(),
                                RequestDocumentation.parameterWithName("page").description("페이지"),
                                RequestDocumentation.parameterWithName("size").description("페이지 당 조회건수").optional()
                                        .attributes(key("defaults").value("20"))
                        ),
                        PayloadDocumentation.responseFields(
                                    PayloadDocumentation.fieldWithPath("[]").description("An array of notices")
                                )
                                            .andWithPrefix("[].", notices)
                ));
    }

    @Test
    @DisplayName("공지사항 수정")
    void noticeEdit() throws Exception {

        NoticeEdit request = NoticeEdit.builder()
                .title("공지사항 수정")
                .content("수정 내용")
                .build();

        String body = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch("/api/notices/{id}",1l)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("notice-edit",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("공지사항 ID")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(key("constraint").value("'바보'가 포함 될 수 없음"))
                                        .optional(),
                                PayloadDocumentation.fieldWithPath("content").description("내용").optional()
                        )
                ));
    }

    @Test
    @DisplayName("공지사항 삭제")
    void noticeDelete() throws Exception {

        User user = userRepository.findById(1l).get();

        List<Notice> requestNotices = IntStream.range(0, 3)
                .mapToObj(i -> Notice.builder()
                        .title("foo"+i)
                        .content("bar"+i)
                        .writer(user)
                        .registrationDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        noticeRepository.saveAll(requestNotices);

        List<Long> ids = new ArrayList<>();
        for (Notice requestNotice : requestNotices) {
            ids.add(requestNotice.getId());
        }

        DeleteIds request = new DeleteIds(ids);

        String body = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/notices")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(document("notice-delete",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("ids").description("공지사항 ID")
                                        .attributes(key("constraints").value("반드시 배열형태로 넘겨주어야 합니다"))
                        )
                ));
    }
}