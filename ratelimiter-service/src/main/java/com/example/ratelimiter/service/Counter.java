package com.example.ratelimiter.service;

import com.example.ratelimiter.dal.RateLimitRule;

public interface Counter {
    void logApi();

    Boolean isMoreApiAllowed();

    RateLimitRule getRateLimitRule();

    Long getCountOfApisInDuration();

    void setRateLimitRule(RateLimitRule rule);
}
