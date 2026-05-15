package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.RateLimitValidateResponse;

public interface RateLimitValidator {
    RateLimitValidateResponse validateRateLimit(ApiSignature apiSignature);
}
