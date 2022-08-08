package com.hanwhalife.poc.api.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DeleteIds {
    List<Long> ids = new ArrayList<>();
}
