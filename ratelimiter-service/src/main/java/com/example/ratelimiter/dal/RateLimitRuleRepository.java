package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface RateLimitRuleRepository {
    RateLimitRule createRateLimit(CreateRateLimitDetails createRateLimitDetails);

    Optional<RateLimitRule> getRateLimit(String id);

    Optional<RateLimitRule> getRateLimit(ApiSignature apiSignature);

    RateLimitRule updateRateLimit(UpdateRateLimitDetails updateRateLimitDetails);

    void deleteRateLimit(String id);
}
