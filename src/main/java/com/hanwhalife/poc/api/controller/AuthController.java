package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public void login(@RequestBody @Valid LoginDto userDto) throws IOException {
        //jwt 적용시 토큰 발급하는 부분.
        log.info("userDto={}", userDto);

        // 이메일 형식 검증.
        // 패스워드 형식 검증.
        userDto.validate();
        authService.login(userDto);

        // 실패시
        // 오류 응답 전달

        // 성공시
        // 권한, 세션 생성

        //200 OK response 생성 전달.
        //response.sendRedirect("/index");
    }

}
