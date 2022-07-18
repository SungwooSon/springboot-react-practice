package com.hanwhalife.poc.api.controller;


import com.hanwhalife.poc.api.dto.UserResponse;
import com.hanwhalife.poc.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> users() {
        return userService.getList();
    }

}
