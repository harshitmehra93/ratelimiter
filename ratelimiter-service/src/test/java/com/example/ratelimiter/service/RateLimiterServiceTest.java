package com.example.ratelimiter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RateLimiterServiceTest {

    @Test
    void createRateLimiter_happy(){
        RateLimiterService test = new RateLimiterService();
        assertEquals("1",test.createRateLimiter("service","api","GET", Duration.ofSeconds(60L),10));
    }
}
