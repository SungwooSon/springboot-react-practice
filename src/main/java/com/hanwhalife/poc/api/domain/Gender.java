package com.hanwhalife.poc.api.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Optional;

public enum Gender {

    MALE("M", "남"),
    FEMALE("F", "여");


    @Getter
    private final String shortCode;

    @Getter
    private final String krCode;

    Gender(String shortCode, String krCode) {
        this.shortCode = shortCode;
        this.krCode = krCode;
    }

    public static Optional<Gender> getGenderByString(String s) {
        return Arrays.stream(values())
                .filter(c -> c.getShortCode().equals(s))
                .findFirst();
    }


}
