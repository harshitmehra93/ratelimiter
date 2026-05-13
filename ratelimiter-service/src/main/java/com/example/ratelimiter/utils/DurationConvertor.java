package com.example.ratelimiter.utils;

import com.example.ratelimiter.api.model.RateLimitDuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationConvertor {
    public static Duration convert(RateLimitDuration duration) {
        ChronoUnit unit = switch (duration.getUnit()){
            case SECONDS -> ChronoUnit.SECONDS;
            case MINUTES -> ChronoUnit.MINUTES;
            case HOURS -> ChronoUnit.HOURS;
            case DAYS -> ChronoUnit.DAYS;
        };
        return Duration.of(duration.getValue(),unit);
    }
}
