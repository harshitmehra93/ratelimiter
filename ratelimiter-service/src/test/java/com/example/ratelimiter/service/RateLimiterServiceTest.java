package com.example.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;

public class RateLimiterServiceTest {

    @Test
    void createRateLimiter_happy() {
        RateLimiterService test = new RateLimiterService();
        assertEquals(
                "1", test.createRateLimiter("service", "api", "GET", Duration.ofSeconds(60L), 10));
    }
}
