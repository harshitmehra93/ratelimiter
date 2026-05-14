package com.example.ratelimiter.model;

import java.time.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class UpdateRateLimitDetails {
    @NonNull private final String id;
    private final String service;
    private final String uri;
    private final String method;
    private final Duration duration;
    private final Integer limit;
}
