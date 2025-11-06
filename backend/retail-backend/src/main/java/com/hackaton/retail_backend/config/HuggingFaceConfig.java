package com.hackaton.retail_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HuggingFaceConfig {

    @Value("${huggingface.api.url:https://api-inference.huggingface.co/models}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Bean
    public WebClient huggingFaceWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
