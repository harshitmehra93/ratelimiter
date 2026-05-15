package com.example.ratelimiter.model;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.service.Counter;
import java.time.Duration;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRateLimitDetails {

    private final ApiSignature apiSignature;
    private final Duration duration;
    private final Integer limit;
    private final Counter counter;
}
