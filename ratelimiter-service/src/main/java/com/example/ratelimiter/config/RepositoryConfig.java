package com.example.ratelimiter.config;

import com.example.ratelimiter.dal.InMemoryRateLimitRepository;
import com.example.ratelimiter.dal.RateLimitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public RateLimitRepository rateLimitRepository() {
        return new InMemoryRateLimitRepository();
    }
}
