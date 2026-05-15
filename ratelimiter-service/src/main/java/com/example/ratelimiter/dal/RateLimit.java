package com.example.ratelimiter.dal;

import com.example.ratelimiter.api.model.ApiSignature;
import java.time.Duration;
import lombok.*;

@Getter
@Setter
public class RateLimit {
    private String id;
    private ApiSignature apiSignature;
    private Duration duration;
    private Integer limit;
}
