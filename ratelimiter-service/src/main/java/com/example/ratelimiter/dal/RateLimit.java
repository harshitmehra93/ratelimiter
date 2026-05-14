package com.example.ratelimiter.dal;

import java.time.Duration;
import lombok.*;

@Getter
@Setter
public class RateLimit {
    private String id;
    private String service;
    private String uri;
    private String method;
    private Duration duration;
    private Integer limit;
}
