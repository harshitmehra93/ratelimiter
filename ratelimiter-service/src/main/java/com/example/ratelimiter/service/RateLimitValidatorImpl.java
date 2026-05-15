package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.RateLimitValidateResponse;
import com.example.ratelimiter.exceptions.RateLimitException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RateLimitValidatorImpl implements RateLimitValidator {
    RateLimitRuleService rateLimitRuleService;

    @Override
    public RateLimitValidateResponse validateRateLimit(ApiSignature apiSignature) {
        var rateLimit = rateLimitRuleService.getRateLimitRule(apiSignature);
        if (rateLimit.isEmpty()) throw new RateLimitException("Rate Limit not found");
        return RateLimitValidateResponse.builder()
                .apiSignature(apiSignature)
                .isAllowed(true)
                .build();
    }
}
