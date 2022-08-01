package com.hanwhalife.poc.api.dto;


import com.hanwhalife.poc.api.domain.Notice;
import org.springframework.data.jpa.domain.Specification;

public class NoticeSpecification {

    public static Specification<Notice> likeKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%"+keyword+"%");
    }
}