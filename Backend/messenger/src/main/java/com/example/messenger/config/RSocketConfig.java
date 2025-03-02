package com.example.messenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

@Configuration
public class RSocketConfig {

    @Bean
    public RSocketMessageHandler rSocketMessageHandler(RSocketStrategies strategies) {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRSocketStrategies(strategies);
        return handler;
    }

    @Bean
    public RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                // Add other strategies here if needed, e.g., encoders, decoders, metadata mappers
                .build();
    }
}