package com.example.ratelimiter.service;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiterService {
    public String createRateLimiter(String service, String api, String method, Duration duration, int limit) {
        return "1";
    }
}
