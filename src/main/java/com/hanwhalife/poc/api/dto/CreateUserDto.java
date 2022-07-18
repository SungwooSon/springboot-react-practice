package com.hanwhalife.poc.api.dto;


import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class CreateUserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 10)
    private String password;

    @NotBlank
    private String username;

    @Size(min = 1, max = 1)
    @Pattern(regexp = "[MF]")
    private String gender;

    @Min(10) @Max(100)
    private int age;

}
