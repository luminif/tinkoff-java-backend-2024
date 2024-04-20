package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CounterConfiguration {
    private final ApplicationConfig config;

    @Bean
    public Counter config(MeterRegistry registry) {
        return Counter
            .builder(config.micrometer().name())
            .description(config.micrometer().description())
            .register(registry);
    }
}
