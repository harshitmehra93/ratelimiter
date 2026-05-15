package com.example.ratelimiter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.ratelimiter.api.model.RateLimitRuleDuration;
import org.junit.jupiter.api.Test;

public class DurationConvertorTest {

    @Test
    void test() {
        DurationConvertor durationConvertor = new DurationConvertor();

        RateLimitRuleDuration SixtySec =
                RateLimitRuleDuration.builder()
                        .unit(RateLimitRuleDuration.UnitEnum.SECONDS)
                        .value(60L)
                        .build();
        RateLimitRuleDuration SixtyMins =
                RateLimitRuleDuration.builder()
                        .unit(RateLimitRuleDuration.UnitEnum.MINUTES)
                        .value(60L)
                        .build();
        RateLimitRuleDuration SixtyHours =
                RateLimitRuleDuration.builder()
                        .unit(RateLimitRuleDuration.UnitEnum.HOURS)
                        .value(60L)
                        .build();
        RateLimitRuleDuration SixtyDays =
                RateLimitRuleDuration.builder()
                        .unit(RateLimitRuleDuration.UnitEnum.DAYS)
                        .value(60L)
                        .build();

        assertEquals(60L, durationConvertor.convert(SixtySec).toSeconds());
        assertEquals(60L, durationConvertor.convert(SixtyMins).toMinutes());
        assertEquals(60L, durationConvertor.convert(SixtyHours).toHours());
        assertEquals(60L, durationConvertor.convert(SixtyDays).toDays());
    }
}
