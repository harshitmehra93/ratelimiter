package com.example.ratelimiter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.CreateRateLimitRuleRequest;
import com.example.ratelimiter.api.model.GetRateLimitRuleResponse;
import com.example.ratelimiter.api.model.RateLimitRuleDuration;
import com.example.ratelimiter.dal.RateLimitRule;
import com.example.ratelimiter.service.RateLimitRuleService;
import java.util.Optional;
import org.junit.jupiter.api.*;

class RatelimiterControllerTest {

    private RateLimiterController rateLimiterController;
    private RateLimitRuleService rateLimitRuleService;

    @BeforeEach
    void setup() {
        rateLimitRuleService = mock(RateLimitRuleService.class);
        rateLimiterController = new RateLimiterController(rateLimitRuleService);
    }

    @Test
    void createRateLimiter_happy() {
        CreateRateLimitRuleRequest request =
                CreateRateLimitRuleRequest.builder()
                        .apiSignature(
                                ApiSignature.builder()
                                        .service("serviceA")
                                        .uri("/some/uri")
                                        .method(ApiSignature.MethodEnum.GET)
                                        .build())
                        .duration(
                                RateLimitRuleDuration.builder()
                                        .unit(RateLimitRuleDuration.UnitEnum.SECONDS)
                                        .value(60L)
                                        .build())
                        .limit(10)
                        .build();
        String rateLimiterId = "123456";
        when(rateLimitRuleService.createRateLimitRule(any(), any(), anyInt()))
                .thenReturn(rateLimiterId);

        var createResponse = rateLimiterController.createRateLimitRule(request);
        assertNotNull(createResponse);
        assertEquals(rateLimiterId, createResponse.getBody().getId());
    }

    @Test
    void getRateLimiter_happy() {
        String rateLimiterId = "123456";
        RateLimitRule rateLimitRule = RateLimitRule.builder().build();
        rateLimitRule.setId(rateLimiterId);
        String serviceA = "serviceA";
        String uri = "/some/uri";
        rateLimitRule.setApiSignature(
                ApiSignature.builder()
                        .service(serviceA)
                        .uri(uri)
                        .method(ApiSignature.MethodEnum.GET)
                        .build());
        rateLimitRule.setDuration(java.time.Duration.ofSeconds(60L));
        rateLimitRule.setLimit(10);
        when(rateLimitRuleService.getRateLimitRule(rateLimiterId))
                .thenReturn(Optional.of(rateLimitRule));

        GetRateLimitRuleResponse response =
                rateLimiterController.getRateLimitRuleById(rateLimiterId).getBody();

        assertNotNull(response);
        assertEquals(rateLimiterId, response.getId());
        assertEquals(serviceA, response.getApiSignature().getService());
        assertEquals(uri, response.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, response.getApiSignature().getMethod());
        assertEquals(10, response.getLimit());
        var duration = response.getDuration();
        assertNotNull(duration);
        assertEquals(60, duration.getValue());
        assertEquals(RateLimitRuleDuration.UnitEnum.SECONDS, duration.getUnit());
    }

    @Test
    void getRateLimiterByServiceUriAndMethod_happy() {
        String service = "serviceA";
        String uri = "/some/uri";
        String method = "GET";
        RateLimitRule rateLimitRule = RateLimitRule.builder().build();
        rateLimitRule.setId("123456");
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(service)
                        .uri(uri)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        rateLimitRule.setApiSignature(apiSignature);
        rateLimitRule.setDuration(java.time.Duration.ofSeconds(60L));
        rateLimitRule.setLimit(10);
        when(rateLimitRuleService.getRateLimitRule(apiSignature))
                .thenReturn(Optional.of(rateLimitRule));

        GetRateLimitRuleResponse response =
                rateLimiterController.getRateLimitRule(service, uri, method).getBody();

        assertNotNull(response);
        assertEquals("123456", response.getId());
        assertEquals(service, response.getApiSignature().getService());
        assertEquals(uri, response.getApiSignature().getUri());
        assertEquals(10, response.getLimit());
        assertEquals(RateLimitRuleDuration.UnitEnum.SECONDS, response.getDuration().getUnit());
        assertEquals(60L, response.getDuration().getValue());
    }
}
