package com.example.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.dal.InMemoryRateLimitRepository;
import com.example.ratelimiter.dal.RateLimit;
import java.time.Duration;
import org.junit.jupiter.api.Test;

public class RateLimiterServiceTest {
    public static final String SERVICE = "service";
    public static final String URI = "/home";
    public static final Duration SIXTY_SECONDS = Duration.ofSeconds(60);
    public static final int LIMIT = 100;

    private RateLimiterService rateLimiterService =
            new RateLimiterService(new InMemoryRateLimitRepository());

    @Test
    void createRateLimiter_happy() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service("service")
                        .uri("uri")
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        String rateLimiter =
                rateLimiterService.createRateLimiter(apiSignature, Duration.ofSeconds(60L), 10);
        assertNotNull(rateLimiter);
    }

    @Test
    void getRateLimiterById() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(SERVICE)
                        .uri(URI)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        String rateLimitId =
                rateLimiterService.createRateLimiter(apiSignature, SIXTY_SECONDS, LIMIT);
        RateLimit rateLimit = rateLimiterService.getRateLimit(rateLimitId);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void getRateLimiterByApiSignature() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(SERVICE)
                        .uri(URI)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        rateLimiterService.createRateLimiter(apiSignature, SIXTY_SECONDS, LIMIT);
        RateLimit rateLimit = rateLimiterService.getRateLimit(apiSignature);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }
}
