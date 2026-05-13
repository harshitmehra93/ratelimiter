package com.example.ratelimiter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.ratelimiter.api.model.RateLimitDuration;
import org.junit.jupiter.api.Test;

public class DurationConvertorTest {

    @Test
    void test() {
        DurationConvertor durationConvertor = new DurationConvertor();

        RateLimitDuration SixtySec =
                RateLimitDuration.builder()
                        .unit(RateLimitDuration.UnitEnum.SECONDS)
                        .value(60)
                        .build();
        RateLimitDuration SixtyMins =
                RateLimitDuration.builder()
                        .unit(RateLimitDuration.UnitEnum.MINUTES)
                        .value(60)
                        .build();
        RateLimitDuration SixtyHours =
                RateLimitDuration.builder()
                        .unit(RateLimitDuration.UnitEnum.HOURS)
                        .value(60)
                        .build();
        RateLimitDuration SixtyDays =
                RateLimitDuration.builder().unit(RateLimitDuration.UnitEnum.DAYS).value(60).build();

        assertEquals(60L, durationConvertor.convert(SixtySec).toSeconds());
        assertEquals(60L, durationConvertor.convert(SixtyMins).toMinutes());
        assertEquals(60L, durationConvertor.convert(SixtyHours).toHours());
        assertEquals(60L, durationConvertor.convert(SixtyDays).toDays());
    }
}
