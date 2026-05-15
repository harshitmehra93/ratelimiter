package com.example.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.RateLimitValidateResponse;
import com.example.ratelimiter.dal.InMemoryRateLimitRuleRepository;
import com.example.ratelimiter.dal.RateLimitRuleRepository;
import com.example.ratelimiter.exceptions.RateLimitException;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateLimitRuleValidatorTest {
    RateLimitValidator rateLimitValidator;
    private RateLimitRuleService rateLimitRuleService;

    @BeforeEach
    void setup() {
        RateLimitRuleRepository rateLimitRuleRepository = new InMemoryRateLimitRuleRepository();
        rateLimitRuleService = new RateLimitRuleService(rateLimitRuleRepository);
        rateLimitValidator = new RateLimitValidatorImpl(rateLimitRuleService);
    }

    @Test
    void validateRateLimit_happy() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service("service")
                        .uri("uri")
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        rateLimitRuleService.createRateLimitRule(apiSignature, Duration.ofSeconds(60L), 100);

        RateLimitValidateResponse rateLimitValidateResponse =
                rateLimitValidator.validateRateLimit(apiSignature);
        assertEquals(Boolean.TRUE, rateLimitValidateResponse.getIsAllowed());
        assertEquals(apiSignature, rateLimitValidateResponse.getApiSignature());
    }

    @Test
    void validateRateLimit_NoRateLimitException() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service("service")
                        .uri("uri")
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        assertThrows(
                RateLimitException.class, () -> rateLimitValidator.validateRateLimit(apiSignature));
    }
}
