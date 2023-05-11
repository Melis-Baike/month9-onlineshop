package com.example.onlineshop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.noCache());
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/resources/")
                .setCacheControl(CacheControl.noCache());
    }
}
