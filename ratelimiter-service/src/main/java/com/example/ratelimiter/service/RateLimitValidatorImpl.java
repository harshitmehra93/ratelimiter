package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.RateLimitValidateResponse;

public class RateLimitValidatorImpl implements RateLimitValidator {
    @Override
    public RateLimitValidateResponse validateRateLimit(ApiSignature apiSignature) {
        return null;
    }
}
