package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.RateLimitValidateResponse;
import com.example.ratelimiter.dal.RateLimitRule;
import com.example.ratelimiter.exceptions.RateLimitException;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RateLimitValidatorImpl implements RateLimitValidator {
    RateLimitRuleService rateLimitRuleService;

    @Override
    public RateLimitValidateResponse validateRateLimit(ApiSignature apiSignature) {
        Optional<RateLimitRule> rateLimit = rateLimitRuleService.getRateLimitRule(apiSignature);
        if (rateLimit.isEmpty()) throw new RateLimitException("Rate Limit not found");

        Counter counter = rateLimit.get().getCounter();
        boolean isAllowed = counter.isMoreApiAllowed();
        if (isAllowed) {
            counter.logApi();
        }

        return RateLimitValidateResponse.builder()
                .apiSignature(apiSignature)
                .isAllowed(isAllowed)
                .build();
    }
}
