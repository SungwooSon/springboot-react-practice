package com.hanwhalife.poc.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000"
                               ,"http://localhost:3001"
                               ,"http://192.168.0.17:3000"    //
                               ,"http://222.232.242.112:3000" // gbsoft 공인 ip
                               ,"http://211.215.6.105:3000"   // 새싹 집 ip
                               ,"http://211.215.6.105:3001"   // 새싹 집 ip
                                );
                                //하나씩 다 입력해줘야되? 이건 아니지..
    }
}
