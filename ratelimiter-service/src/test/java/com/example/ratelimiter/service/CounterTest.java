package com.example.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.dal.RateLimitRule;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CounterTest {

    private Counter counter;
    private RateLimitRule rule;

    @BeforeEach
    private void setup() {
        rule =
                RateLimitRule.builder()
                        .id("ID")
                        .counter(mock(Counter.class))
                        .apiSignature(ApiSignature.builder().build())
                        .duration(Duration.ofSeconds(60L))
                        .limit(100)
                        .build();
        counter = new CounterImpl(rule);
    }

    @Test
    void getRateLimitRule() {
        assertEquals(rule, counter.getRateLimitRule());
    }

    @Test
    void logApi() {
        counter.logApi();
    }

    @Test
    void getCountOfApisInDuration() {
        rule =
                RateLimitRule.builder()
                        .apiSignature(ApiSignature.builder().build())
                        .duration(Duration.ofSeconds(60L))
                        .limit(100)
                        .build();
        counter = new CounterImpl(rule);

        for (int i = 0; i < 1000; i++) counter.logApi();

        assertEquals(1000, counter.getCountOfApisInDuration());
    }

    @Test
    void isAllowed_yes() {
        rule =
                RateLimitRule.builder()
                        .apiSignature(ApiSignature.builder().build())
                        .duration(Duration.ofSeconds(60L))
                        .limit(2)
                        .build();
        counter = new CounterImpl(rule);
        counter.logApi();

        assertTrue(counter.isMoreApiAllowed());
    }

    @Test
    void isAllowed_no() {
        rule =
                RateLimitRule.builder()
                        .apiSignature(ApiSignature.builder().build())
                        .duration(Duration.ofSeconds(60L))
                        .limit(2)
                        .build();
        counter = new CounterImpl(rule);
        counter.logApi();
        counter.logApi();

        assertFalse(counter.isMoreApiAllowed());
    }
}
