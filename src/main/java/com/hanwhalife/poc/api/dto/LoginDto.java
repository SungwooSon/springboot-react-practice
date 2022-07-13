package com.hanwhalife.poc.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString()
@Builder
@Getter
public class LoginDto {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;

    public void validate() {
        //validateEmail();
        //validatePassword();

        if (!email.contains("@")) {
            //throw new InvalidRequestException("이메일 형식이 아님");
        }
    }

}
