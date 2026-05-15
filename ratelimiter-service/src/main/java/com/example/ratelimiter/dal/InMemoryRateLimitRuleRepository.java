package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.exceptions.RateLimitException;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRateLimitRuleRepository implements RateLimitRuleRepository {

    private final Map<String, RateLimitRule> rateLimitRules = new ConcurrentHashMap<>();

    @Override
    public RateLimitRule createRateLimit(CreateRateLimitDetails createRateLimitDetails) {
        if (getRateLimit(createRateLimitDetails.getApiSignature()).isPresent())
            throw new RateLimitException("Rate Limit already exists");
        RateLimitRule rateLimitRule = new RateLimitRule();
        rateLimitRule.setId(UUID.randomUUID().toString());
        rateLimitRule.setApiSignature(createRateLimitDetails.getApiSignature());
        rateLimitRule.setDuration(createRateLimitDetails.getDuration());
        rateLimitRule.setLimit(createRateLimitDetails.getLimit());
        rateLimitRules.put(rateLimitRule.getId(), rateLimitRule);
        return rateLimitRule;
    }

    @Override
    public Optional<RateLimitRule> getRateLimit(String id) {
        if (rateLimitRules.containsKey(id)) return Optional.of(rateLimitRules.get(id));
        return Optional.empty();
    }

    @Override
    public Optional<RateLimitRule> getRateLimit(ApiSignature apiSignature) {
        return rateLimitRules.values().stream()
                .filter(rateLimit -> rateLimit.getApiSignature().equals(apiSignature))
                .findFirst();
    }

    @Override
    public RateLimitRule updateRateLimit(UpdateRateLimitDetails updateRateLimitDetails) {
        Optional<RateLimitRule> rateLimitRule = Optional.empty();
        if (updateRateLimitDetails.getId() != null) {
            rateLimitRule = getRateLimit(updateRateLimitDetails.getId());
        }
        if (updateRateLimitDetails.getId() == null) {
            rateLimitRule = getRateLimit(updateRateLimitDetails.getApiSignature());
        }
        if (rateLimitRule.isEmpty()) throw new RateLimitException("Rate limit does not exist");

        rateLimitRule.get().setDuration(updateRateLimitDetails.getDuration());
        rateLimitRule.get().setLimit(updateRateLimitDetails.getLimit());
        return rateLimitRule.get();
    }

    @Override
    public void deleteRateLimit(String id) {
        if (getRateLimit(id).isEmpty()) return;
        rateLimitRules.remove(id);
    }
}
