package com.hanwhalife.poc.test;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BuilderTest {

    @Test
    @DisplayName("SuperBuiler를 사용해서 부모의 필드까지 builder로 만든다.")
    public void superBuilderTest() {

        ListDto dto = ListDto.builder()
                .id(1L)
                .name("test")
                .build();

        Assertions.assertThat(dto.getId()).isEqualTo(1L);
        Assertions.assertThat(dto.getName()).isEqualTo("test");
    }


    @SuperBuilder
    @Getter
    static class ListDto extends BaseListDtoAbstract {
        private Long id;
    }

    @SuperBuilder
    @Getter
    static abstract class BaseListDtoAbstract {
        private String name;
    }
}
