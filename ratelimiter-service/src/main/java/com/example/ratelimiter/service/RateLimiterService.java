package com.example.ratelimiter.service;

import com.example.ratelimiter.dal.RateLimit;
import com.example.ratelimiter.dal.RateLimitRepository;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final RateLimitRepository rateLimitRepository;

    public RateLimiterService(RateLimitRepository rateLimitRepository) {
        this.rateLimitRepository = rateLimitRepository;
    }

    public String createRateLimiter(
            String service, String uri, String method, Duration duration, int limit) {
        return rateLimitRepository
                .createRateLimit(
                        CreateRateLimitDetails.builder()
                                .service(service)
                                .uri(uri)
                                .method(method)
                                .duration(duration)
                                .limit(limit)
                                .build())
                .getId();
    }

    public RateLimit getRateLimit(String id) {
        return rateLimitRepository.getRateLimit(id);
    }

    public RateLimit getRateLimit(String service, String uri, String method) {
        return rateLimitRepository.getRateLimit(service, uri, method);
    }
}
