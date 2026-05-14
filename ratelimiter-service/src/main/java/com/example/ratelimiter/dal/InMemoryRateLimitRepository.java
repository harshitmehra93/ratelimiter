package com.example.ratelimiter.dal;

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
        rateLimit.setService(createRateLimitDetails.getService());
        rateLimit.setUri(createRateLimitDetails.getUri());
        rateLimit.setMethod(createRateLimitDetails.getMethod());
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
    public RateLimit getRateLimit(String service, String uri, String method) {
        return rateLimits.values().stream()
                .filter(
                        rateLimit ->
                                rateLimit.getService().equals(service)
                                        && rateLimit.getUri().equals(uri)
                                        && rateLimit.getMethod().equals(method))
                .findFirst()
                .orElse(null);
    }

    @Override
    public RateLimit updateRateLimit(UpdateRateLimitDetails updateRateLimitDetails) {
        RateLimit rateLimit;
        if (updateRateLimitDetails.getId() != null) {
            rateLimit = getRateLimit(updateRateLimitDetails.getId());
        } else {
            rateLimit =
                    getRateLimit(
                            updateRateLimitDetails.getService(),
                            updateRateLimitDetails.getUri(),
                            updateRateLimitDetails.getMethod());
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
