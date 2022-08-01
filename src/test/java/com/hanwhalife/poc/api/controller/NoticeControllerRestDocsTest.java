package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.dto.NoticeCreate;
import com.hanwhalife.poc.api.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;

import static com.hanwhalife.poc.api.restdocs.RestDocsConfig.field;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockBean(JpaMetamodelMappingContext.class)
public class NoticeControllerRestDocsTest extends RestDocsTestSupport {

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
                .andDo(
                        restDocs.document(
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("title").description("제목").attributes(field("constraint", "바보가 포함될 수 없음")),
                                        PayloadDocumentation.fieldWithPath("content").description("내용"),
                                        PayloadDocumentation.fieldWithPath("userId").description("작성자 ID")
                                )
                        )
                );
    }

}
