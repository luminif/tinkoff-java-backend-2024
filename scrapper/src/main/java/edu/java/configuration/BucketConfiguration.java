package edu.java.configuration;

import io.github.bucket4j.Bucket;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfiguration {
    @Value("${bucket.tokens}")
    private int tokens;
    @Value("${bucket.refill-tokens}")
    private int refillTokens;

    @Bean
    public Bucket createNewBucket() {
        return Bucket
            .builder()
            .addLimit(limit ->
                limit.capacity(tokens)
                    .refillGreedy(
                        refillTokens,
                        Duration.ofSeconds(1)
                    ))
            .build();
    }
}
