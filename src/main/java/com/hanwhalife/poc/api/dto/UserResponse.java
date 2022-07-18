package com.hanwhalife.poc.api.dto;

import com.hanwhalife.poc.api.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String gender;
    private String married;
    private String ageGroup;
    private String jobGroup;
    private String jobPosition;
    private String address;
    private String firstLoginDateTime;

    @Builder
    public UserResponse(Long id, String email, Gender gender, Married married, Age age, JobGroup jobGroup, JobPosition jobPosition, Address address, String firstLoginDateTime) {
        this.id = id;
        this.email = email;
        this.gender = gender.getKrCode();
        this.married = married.getKrCode();
        this.ageGroup = age.convertAgeGroup();
        this.jobGroup = jobGroup.getKrCode();
        this.jobPosition = jobPosition.getKrCode();
        this.address = address.getFullAddress();
        this.firstLoginDateTime = firstLoginDateTime;
    }
}
