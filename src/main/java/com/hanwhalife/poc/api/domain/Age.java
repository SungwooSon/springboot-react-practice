package com.hanwhalife.poc.api.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {

    private Integer age;


    public Age(Integer age) {
        validate(age);
        this.age = age;
    }

    public static Age getAgeByInt(int age) {
        return new Age(age);
    }

    private void validate(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("nono");
        }
    }

    public String convertAgeGroup() {
        return ((int)(this.age / 10 * 10)) + "대";
    }
}
