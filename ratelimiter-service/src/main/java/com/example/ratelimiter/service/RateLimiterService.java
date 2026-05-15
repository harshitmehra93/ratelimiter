package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
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

    public String createRateLimiter(ApiSignature apiSignature, Duration duration, int limit) {
        return rateLimitRepository
                .createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(apiSignature)
                                .duration(duration)
                                .limit(limit)
                                .build())
                .getId();
    }

    public RateLimit getRateLimit(String id) {
        return rateLimitRepository.getRateLimit(id);
    }

    public RateLimit getRateLimit(ApiSignature apiSignature) {
        return rateLimitRepository.getRateLimit(apiSignature);
    }
}
