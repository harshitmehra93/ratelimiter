package com.example.ratelimiter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ratelimiter.api.model.CreateRateLimitRequest;
import com.example.ratelimiter.api.model.GetRateLimitResponse;
import com.example.ratelimiter.api.model.RateLimitDuration;
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
                        .service("serviceA")
                        .api("/some/uri")
                        .method(CreateRateLimitRequest.MethodEnum.GET)
                        .duration(
                                RateLimitDuration.builder()
                                        .unit(RateLimitDuration.UnitEnum.SECONDS)
                                        .value(60)
                                        .build())
                        .limit(10)
                        .build();
        String rateLimiterId = "123456";
        when(rateLimiterService.createRateLimiter(any(), any(), any(), any(), anyInt()))
                .thenReturn(rateLimiterId);

        var createResponse = rateLimiterController.createRateLimiter(request);
        assertNotNull(createResponse);
        assertEquals(rateLimiterId, createResponse.getBody().getId());
    }

    @Test
    void getRateLimiter_happy() {
        String rateLimiterId = "123456";

        GetRateLimitResponse response =
                rateLimiterController.getRateLimiter(rateLimiterId).getBody();

        assertNotNull(response);
        assertEquals(rateLimiterId, response.getId());
        assertEquals("serviceA", response.getService());
        assertEquals("/some/uri", response.getApi());
        assertEquals(10, response.getLimit());
        var duration = response.getDuration();
        assertNotNull(duration);
        assertEquals(60, duration.getValue());
        assertEquals(RateLimitDuration.UnitEnum.SECONDS, duration.getUnit());
    }
}
