package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRateLimitRepository implements RateLimitRepository {

    private final Map<String, RateLimit> rateLimits = new ConcurrentHashMap<>();

    @Override
    public RateLimit createRateLimit(CreateRateLimitDetails createRateLimitDetails) {
        RateLimit rateLimit = new RateLimit();
        rateLimit.setId(UUID.randomUUID().toString());
        rateLimit.setApiSignature(createRateLimitDetails.getApiSignature());
        rateLimit.setDuration(createRateLimitDetails.getDuration());
        rateLimit.setLimit(createRateLimitDetails.getLimit());
        rateLimits.put(rateLimit.getId(), rateLimit);
        return rateLimit;
    }

    @Override
    public RateLimit getRateLimit(String id) {
        return rateLimits.get(id);
    }

    @Override
    public RateLimit getRateLimit(ApiSignature apiSignature) {
        return rateLimits.values().stream()
                .filter(rateLimit -> rateLimit.getApiSignature().equals(apiSignature))
                .findFirst()
                .orElse(null);
    }

    @Override
    public RateLimit updateRateLimit(UpdateRateLimitDetails updateRateLimitDetails) {
        RateLimit rateLimit;
        if (updateRateLimitDetails.getId() != null) {
            rateLimit = getRateLimit(updateRateLimitDetails.getId());
        } else {
            rateLimit = getRateLimit(updateRateLimitDetails.getApiSignature());
        }

        if (rateLimit == null) {
            return null;
        }
        rateLimit.setDuration(updateRateLimitDetails.getDuration());
        rateLimit.setLimit(updateRateLimitDetails.getLimit());
        return rateLimit;
    }

    @Override
    public void deleteRateLimit(String id) {
        rateLimits.remove(id);
    }
}
