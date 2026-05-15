package com.example.ratelimiter.service;

import com.example.ratelimiter.dal.RateLimitRule;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CounterImpl implements Counter {
    RateLimitRule rateLimitRule;
    List<Instant> apis = new ArrayList<>();

    CounterImpl(RateLimitRule rateLimitRule) {
        this.rateLimitRule = rateLimitRule;
    }

    @Override
    public void logApi() {
        apis.add(Instant.now());
    }

    @Override
    public Boolean isMoreApiAllowed() {
        if (getCountOfApisInDuration() >= getRateLimitRule().getLimit()) return false;
        return true;
    }

    @Override
    public RateLimitRule getRateLimitRule() {
        return rateLimitRule;
    }

    @Override
    public Long getCountOfApisInDuration() {
        Instant cutoff = Instant.now().minusSeconds(rateLimitRule.getDuration().toSeconds());
        return apis.stream().filter(instant -> instant.isAfter(cutoff)).count();
    }

    @Override
    public void setRateLimitRule(RateLimitRule rule) {
        rateLimitRule = rule;
    }
}
