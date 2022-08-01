package com.hanwhalife.poc.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
    //@SpringBootApplication에 @EnableJpaAuditing를 붙여두면,
    //@WebMvcTest같은 슬라이스 테스트는 JPA 관련 Bean들을 로드하지 않기 때문에 에러가 발생.

    //그래서 따로 Configuration 클래스를 만들어서 @EnableJpaAuditing를 분리함.
}
