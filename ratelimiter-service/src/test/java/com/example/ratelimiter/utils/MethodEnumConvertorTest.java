package com.example.ratelimiter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.ratelimiter.api.model.GetRateLimitResponse;
import org.junit.jupiter.api.Test;

class MethodEnumConvertorTest {

    @Test
    void convert_shouldConvertSupportedHttpMethods() {
        assertEquals(GetRateLimitResponse.MethodEnum.GET, MethodEnumConvertor.convert("GET"));
        assertEquals(GetRateLimitResponse.MethodEnum.POST, MethodEnumConvertor.convert("POST"));
        assertEquals(GetRateLimitResponse.MethodEnum.PUT, MethodEnumConvertor.convert("PUT"));
        assertEquals(GetRateLimitResponse.MethodEnum.PATCH, MethodEnumConvertor.convert("PATCH"));
        assertEquals(GetRateLimitResponse.MethodEnum.DELETE, MethodEnumConvertor.convert("DELETE"));
    }

    @Test
    void convert_shouldNormalizeInput() {
        assertEquals(GetRateLimitResponse.MethodEnum.GET, MethodEnumConvertor.convert(" get "));
    }

    @Test
    void convert_shouldRejectInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> MethodEnumConvertor.convert("OPTIONS"));
        assertThrows(IllegalArgumentException.class, () -> MethodEnumConvertor.convert(null));
    }
}
