package com.hanwhalife.poc.api.domain;

import lombok.Getter;

public enum JobGroup {

    EMPLOYEE("사원"),
    D("대리"),
    G("과장"),
    I("임원");

    @Getter
    private final String krCode;

    JobGroup(String krCode) {
        this.krCode = krCode;
    }

}
