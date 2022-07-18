package com.hanwhalife.poc.api.domain;

import lombok.Getter;

public enum Married {

    Y("기혼"),
    N("미혼");

    @Getter
    private final String krCode;

    Married(String krCode) {
        this.krCode = krCode;
    }


}
