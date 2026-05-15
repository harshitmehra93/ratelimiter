package com.example.ratelimiter.model;

import com.example.ratelimiter.api.model.ApiSignature;
import java.time.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class UpdateRateLimitDetails {
    @NonNull private final String id;
    private final ApiSignature apiSignature;
    private final Duration duration;
    private final Integer limit;
}
