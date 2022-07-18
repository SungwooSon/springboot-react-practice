package com.hanwhalife.poc.api.domain;

import lombok.Getter;

public enum JobPosition {

    IT("IT 개발/운영"),
    MANAGEMENT("경영"),
    SALES("영업")
    ;

    @Getter
    private final String krCode;

    JobPosition(String krCode) {
        this.krCode = krCode;
    }
}
