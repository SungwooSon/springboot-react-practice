package com.hanwhalife.poc.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteIds {
    private List<Long> ids;

    public DeleteIds(List<Long> ids) {
        this.ids = ids;
    }
}
