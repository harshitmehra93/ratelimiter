package com.example.ratelimiter.config;

import com.example.ratelimiter.dal.InMemoryRateLimitRuleRepository;
import com.example.ratelimiter.dal.RateLimitRuleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public RateLimitRuleRepository rateLimitRepository() {
        return new InMemoryRateLimitRuleRepository();
    }
}
