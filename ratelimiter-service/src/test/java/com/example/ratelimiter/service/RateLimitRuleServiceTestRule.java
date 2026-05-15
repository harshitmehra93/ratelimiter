package com.example.ratelimiter.service;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.dal.InMemoryRateLimitRuleRepository;
import com.example.ratelimiter.dal.RateLimitRule;
import java.time.Duration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimitRuleServiceTestRule {
    public static final String SERVICE = "service";
    public static final String URI = "/home";
    public static final Duration SIXTY_SECONDS = Duration.ofSeconds(60);
    public static final int LIMIT = 100;

    private RateLimitRuleService rateLimitRuleService =
            new RateLimitRuleService(new InMemoryRateLimitRuleRepository());

    @Test
    void createRateLimiter_happy() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service("service")
                        .uri("uri")
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        String rateLimiter =
                rateLimitRuleService.createRateLimitRule(apiSignature, Duration.ofSeconds(60L), 10);
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
                rateLimitRuleService.createRateLimitRule(apiSignature, SIXTY_SECONDS, LIMIT);
        RateLimitRule rateLimitRule = rateLimitRuleService.getRateLimitRule(rateLimitId).get();
        assertNotNull(rateLimitRule.getId());
        assertEquals(SERVICE, rateLimitRule.getApiSignature().getService());
        assertEquals(URI, rateLimitRule.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimitRule.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimitRule.getDuration());
        assertEquals(LIMIT, rateLimitRule.getLimit());
    }

    @Test
    void getRateLimiterByApiSignature() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(SERVICE)
                        .uri(URI)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        rateLimitRuleService.createRateLimitRule(apiSignature, SIXTY_SECONDS, LIMIT);
        RateLimitRule rateLimitRule = rateLimitRuleService.getRateLimitRule(apiSignature).get();
        assertNotNull(rateLimitRule.getId());
        assertEquals(SERVICE, rateLimitRule.getApiSignature().getService());
        assertEquals(URI, rateLimitRule.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimitRule.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimitRule.getDuration());
        assertEquals(LIMIT, rateLimitRule.getLimit());
    }

    @Test
    void getRateLimit_DoesNotExist_Throws(){
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(SERVICE)
                        .uri(URI)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        assertTrue(rateLimitRuleService.getRateLimitRule(apiSignature).isEmpty());
    }

    @Test
    void getRateLimitById_DoesNotExist_Throws(){
        assertTrue(rateLimitRuleService.getRateLimitRule("ID").isEmpty());
    }
}
