package com.hanwhalife.poc.test.service.login;

import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.exception.UserNotFoundException;
import com.hanwhalife.poc.api.service.AuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService loginService;

    @Test
    @DisplayName("비밀번호 또는 이메일이 틀리면 에러가 발생한다")
    public void 전달인자_테스트() {
        //given

        //given + expected
        Assertions.assertThatThrownBy(() -> loginService.login(LoginDto.builder()
                                                                        .email("admin@gb-soft.com")
                                                                        .password("1233")
                                                                        .build()))
                   .isInstanceOf(UserNotFoundException.class);

        Assertions.assertThatThrownBy(() -> loginService.login(LoginDto.builder()
                        .email("admin2@gb-soft.com")
                        .password("1234")
                        .build()))
                .isInstanceOf(UserNotFoundException.class);

    }

}
