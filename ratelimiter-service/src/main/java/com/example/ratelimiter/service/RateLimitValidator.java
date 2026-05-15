package com.example.ratelimiter.service;

public interface RateLimitValidator {
    RateLimitValidateResponse validateRateLimit(RateLimitValidateRequest rateLimitValidateRequest);
}
