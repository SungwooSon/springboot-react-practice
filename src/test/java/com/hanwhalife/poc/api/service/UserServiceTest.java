package com.hanwhalife.poc.api.service;

import com.hanwhalife.poc.api.dto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("로딩시점에 입력한 아이의 정보를 확인해본다")
    public void user1Test() {
        List<UserResponse> list = userService.getList();
        assertThat(list.size()).isEqualTo(3);

        UserResponse dto = list.get(0);
        assertThat(dto.getGender()).isEqualTo("남");
        assertThat(dto.getMarried()).isEqualTo("기혼");
        assertThat(dto.getAgeGroup()).isEqualTo("30대");
        assertThat(dto.getJobGroup()).isEqualTo("사원");
        assertThat(dto.getJobPosition()).isEqualTo("IT 개발/운영");
        assertThat(dto.getAddress()).isEqualTo("서울시 강남구 대치동");
    }
}
