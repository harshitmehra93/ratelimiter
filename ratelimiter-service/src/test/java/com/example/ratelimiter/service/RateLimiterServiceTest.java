package com.example.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.ratelimiter.dal.InMemoryRateLimitRepository;
import java.time.Duration;
import org.junit.jupiter.api.Test;

public class RateLimiterServiceTest {

    private RateLimiterService rateLimiterService =
            new RateLimiterService(new InMemoryRateLimitRepository());

    @Test
    void createRateLimiter_happy() {
        assertNotNull(
                rateLimiterService.createRateLimiter(
                        "service", "api", "GET", Duration.ofSeconds(60L), 10));
    }
}
