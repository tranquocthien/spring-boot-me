package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // Các domain được phép truy cập API của bạn
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức HTTP được phép
                .allowedHeaders("*"); // Các header được phép (có thể chỉ định cụ thể)
    }
}