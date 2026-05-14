package com.example.ratelimiter.model;

import java.time.Duration;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRateLimitDetails {

    private final String service;
    private final String uri;
    private final String method;
    private final Duration duration;
    private final Integer limit;
}
