package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.request.NoticeCreate;
import com.hanwhalife.poc.api.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;

import static com.hanwhalife.poc.api.restdocs.RestDocsConfig.field;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockBean(JpaMetamodelMappingContext.class)
public class NoticeControllerRestDocsTest extends RestDocsTestSupport {

    //@Test
    @DisplayName("공지 작성")
    void noticeCreate() throws Exception {
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

    //@Test
    @DisplayName("공지 리스트 조회")
    void noticeListInquiry() throws Exception {

        FieldDescriptor[] notices = new FieldDescriptor[] {
                PayloadDocumentation.fieldWithPath("id").description("공지사항 ID"),
                PayloadDocumentation.fieldWithPath("title").description("제목"),
                PayloadDocumentation.fieldWithPath("content").description("내용"),
                PayloadDocumentation.fieldWithPath("writer").description("작성자 ID"),
                PayloadDocumentation.fieldWithPath("registrationDate").description("작성일자")
        };

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/notices")
                                .accept(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                RequestDocumentation.requestParameters(
                                        RequestDocumentation.parameterWithName("keyword").description("공지사항 제목의 일부분").optional()
                                ),
                                PayloadDocumentation.responseFields(
                                                PayloadDocumentation.fieldWithPath("[]").description("An array of notices")
                                        )
                                        .andWithPrefix("[].", notices)
                        )


                );
    }

    //@Test
    @DisplayName("공지 단건 조회")
    void noticeInquiry() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/notices/{id}", 1l)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
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
                        )
                );
    }

}
