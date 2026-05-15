package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.service.Counter;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.*;

@Getter
@Setter
@Builder
public class RateLimitRule {
    @NotNull private String id;
    @NotNull private ApiSignature apiSignature;
    @NotNull private Duration duration;
    @NotNull private Integer limit;
    @NotNull private Counter counter;
}
