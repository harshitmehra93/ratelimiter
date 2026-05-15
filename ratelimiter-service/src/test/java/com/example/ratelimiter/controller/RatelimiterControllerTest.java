package com.example.ratelimiter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.api.model.CreateRateLimitRequest;
import com.example.ratelimiter.api.model.GetRateLimitResponse;
import com.example.ratelimiter.api.model.RateLimitDuration;
import com.example.ratelimiter.dal.RateLimit;
import com.example.ratelimiter.service.RateLimiterService;
import org.junit.jupiter.api.*;

class RatelimiterControllerTest {

    private RateLimiterController rateLimiterController;
    private RateLimiterService rateLimiterService;

    @BeforeEach
    void setup() {
        rateLimiterService = mock(RateLimiterService.class);
        rateLimiterController = new RateLimiterController(rateLimiterService);
    }

    @Test
    void createRateLimiter_happy() {
        CreateRateLimitRequest request =
                CreateRateLimitRequest.builder()
                        .apiSignature(
                                ApiSignature.builder()
                                        .service("serviceA")
                                        .uri("/some/uri")
                                        .method(ApiSignature.MethodEnum.GET)
                                        .build())
                        .duration(
                                RateLimitDuration.builder()
                                        .unit(RateLimitDuration.UnitEnum.SECONDS)
                                        .value(60L)
                                        .build())
                        .limit(10)
                        .build();
        String rateLimiterId = "123456";
        when(rateLimiterService.createRateLimiter(any(), any(), anyInt()))
                .thenReturn(rateLimiterId);

        var createResponse = rateLimiterController.createRateLimiter(request);
        assertNotNull(createResponse);
        assertEquals(rateLimiterId, createResponse.getBody().getId());
    }

    @Test
    void getRateLimiter_happy() {
        String rateLimiterId = "123456";
        RateLimit rateLimit = new RateLimit();
        rateLimit.setId(rateLimiterId);
        String serviceA = "serviceA";
        String uri = "/some/uri";
        rateLimit.setApiSignature(
                ApiSignature.builder()
                        .service(serviceA)
                        .uri(uri)
                        .method(ApiSignature.MethodEnum.GET)
                        .build());
        rateLimit.setDuration(java.time.Duration.ofSeconds(60L));
        rateLimit.setLimit(10);
        when(rateLimiterService.getRateLimit(rateLimiterId)).thenReturn(rateLimit);

        GetRateLimitResponse response =
                rateLimiterController.getRateLimiterById(rateLimiterId).getBody();

        assertNotNull(response);
        assertEquals(rateLimiterId, response.getId());
        assertEquals(serviceA, response.getApiSignature().getService());
        assertEquals(uri, response.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, response.getApiSignature().getMethod());
        assertEquals(10, response.getLimit());
        var duration = response.getDuration();
        assertNotNull(duration);
        assertEquals(60, duration.getValue());
        assertEquals(RateLimitDuration.UnitEnum.SECONDS, duration.getUnit());
    }

    @Test
    void getRateLimiterByServiceUriAndMethod_happy() {
        String service = "serviceA";
        String uri = "/some/uri";
        String method = "GET";
        RateLimit rateLimit = new RateLimit();
        rateLimit.setId("123456");
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(service)
                        .uri(uri)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        rateLimit.setApiSignature(apiSignature);
        rateLimit.setDuration(java.time.Duration.ofSeconds(60L));
        rateLimit.setLimit(10);
        when(rateLimiterService.getRateLimit(apiSignature)).thenReturn(rateLimit);

        GetRateLimitResponse response =
                rateLimiterController.getRateLimiter(service, uri, method).getBody();

        assertNotNull(response);
        assertEquals("123456", response.getId());
        assertEquals(service, response.getApiSignature().getService());
        assertEquals(uri, response.getApiSignature().getUri());
        assertEquals(10, response.getLimit());
        assertEquals(RateLimitDuration.UnitEnum.SECONDS, response.getDuration().getUnit());
        assertEquals(60L, response.getDuration().getValue());
    }
}
