package com.hanwhalife.poc.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwhalife.poc.api.domain.Notice;
import com.hanwhalife.poc.api.dto.NoticeCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.poc.hanwhalife.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(key("constraint").value("'바보'가 포함 될 수 없음")),
                                PayloadDocumentation.fieldWithPath("content").description("내용").optional(),
                                PayloadDocumentation.fieldWithPath("userId").description("작성자 ID").optional()
                        )
                ));
    }

    @Test
    @DisplayName("공지 단건 조회")
    void findOneNotice() throws Exception {

        //String body = objectMapper.writeValueAsString(request);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/notices/{id}", 1l)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("notice-inquiry",
                        RequestDocumentation.pathParameters(
                          RequestDocumentation.parameterWithName("id").description("공지 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("공지 ID")
                                        .attributes(key("constraint").value("'바보'가 포함 될 수 없음")),
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(key("constraint").value("'바보'가 포함 될 수 없음")),
                                PayloadDocumentation.fieldWithPath("content").description("내용").optional(),
                                PayloadDocumentation.fieldWithPath("writer").description("작성자 ID").optional(),
                                PayloadDocumentation.fieldWithPath("registerDate").description("작성일자").optional()
                        )
                ));
    }
}
