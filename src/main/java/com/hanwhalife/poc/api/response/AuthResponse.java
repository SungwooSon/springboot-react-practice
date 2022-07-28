package com.hanwhalife.poc.api.response;

import com.hanwhalife.poc.api.domain.User;
import com.hanwhalife.poc.api.dto.LoginDto;
import com.hanwhalife.poc.api.dto.UserAccountDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {

    private String code;
    private String message;

    private LoginDto user;

}
