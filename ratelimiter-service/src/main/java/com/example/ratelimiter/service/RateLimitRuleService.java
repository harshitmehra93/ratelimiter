package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.dal.RateLimitRule;
import com.example.ratelimiter.dal.RateLimitRuleRepository;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RateLimitRuleService {
    private final RateLimitRuleRepository rateLimitRuleRepository;

    public RateLimitRuleService(RateLimitRuleRepository rateLimitRuleRepository) {
        this.rateLimitRuleRepository = rateLimitRuleRepository;
    }

    public String createRateLimitRule(ApiSignature apiSignature, Duration duration, int limit) {
        return rateLimitRuleRepository
                .createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(apiSignature)
                                .duration(duration)
                                .limit(limit)
                                .build())
                .getId();
    }

    public Optional<RateLimitRule> getRateLimitRule(String id) {
        return rateLimitRuleRepository.getRateLimit(id);
    }

    public Optional<RateLimitRule> getRateLimitRule(ApiSignature apiSignature) {
        return rateLimitRuleRepository.getRateLimit(apiSignature);
    }
}
