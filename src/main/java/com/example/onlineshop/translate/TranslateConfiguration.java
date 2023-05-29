package com.example.onlineshop.translate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslateConfiguration {

    @Bean
    public Translate translate() {
        return TranslateOptions.getDefaultInstance().getService();
    }
}

