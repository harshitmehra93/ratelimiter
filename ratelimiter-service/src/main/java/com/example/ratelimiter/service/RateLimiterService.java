package com.example.ratelimiter.service;

import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterService {
    public String createRateLimiter(
            String service, String api, String method, Duration duration, int limit) {
        return "1";
    }
}
