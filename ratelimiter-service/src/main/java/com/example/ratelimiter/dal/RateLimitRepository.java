package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import org.springframework.stereotype.Component;

@Component
public interface RateLimitRepository {
    RateLimit createRateLimit(CreateRateLimitDetails createRateLimitDetails);

    RateLimit getRateLimit(String id);

    RateLimit getRateLimit(ApiSignature apiSignature);

    RateLimit updateRateLimit(UpdateRateLimitDetails updateRateLimitDetails);

    void deleteRateLimit(String id);
}
