package com.hanwhalife.poc.api.controller;

import com.hanwhalife.poc.api.dto.CreateUserDto;
import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public void login(@RequestBody @Valid LoginDto userDto, HttpServletRequest request) throws IOException {
        //jwt 적용시 토큰 발급하는 부분.
        log.info("userDto={}", userDto);
        log.info("loginIp={}", getRemoteAddr(request));

        // 이메일 형식 검증.
        // 패스워드 형식 검증.
        userDto.validate();
        authService.login(userDto, getRemoteAddr(request));

        // 실패시
        // 오류 응답 전달

        // 성공시
        // 권한, 세션 생성

        //200 OK response 생성 전달.
        //response.sendRedirect("/index");
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid CreateUserDto createUserDto) {
        authService.signUp(createUserDto);
    }


    protected String getRemoteAddr(HttpServletRequest request){
        return (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
    }

}
