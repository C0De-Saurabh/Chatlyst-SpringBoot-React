package com.example.messenger.config;

import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Configuration
public class RSocketConfig {

    @Bean
    public Mono<RSocketRequester> rSocketRequester(RSocketRequester.Builder builder, RSocketStrategies strategies) {
        return Mono.fromSupplier(() -> builder
                        .rsocketStrategies(strategies)
                        .transport(TcpClientTransport.create("localhost", 7000)))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))); // Retries on failure
    }
}
