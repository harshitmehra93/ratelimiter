package com.example.ratelimiter.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.ratelimiter.api.model.ApiSignature;
import org.junit.jupiter.api.Test;

class MethodEnumConvertorTest {

    @Test
    void convert_shouldConvertSupportedHttpMethods() {
        assertEquals(ApiSignature.MethodEnum.GET, MethodEnumConvertor.convert("GET"));
        assertEquals(ApiSignature.MethodEnum.POST, MethodEnumConvertor.convert("POST"));
        assertEquals(ApiSignature.MethodEnum.PUT, MethodEnumConvertor.convert("PUT"));
        assertEquals(ApiSignature.MethodEnum.PATCH, MethodEnumConvertor.convert("PATCH"));
        assertEquals(ApiSignature.MethodEnum.DELETE, MethodEnumConvertor.convert("DELETE"));
    }

    @Test
    void convert_shouldNormalizeInput() {
        assertEquals(ApiSignature.MethodEnum.GET, MethodEnumConvertor.convert(" get "));
    }

    @Test
    void convert_shouldRejectInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> MethodEnumConvertor.convert("OPTIONS"));
        assertThrows(IllegalArgumentException.class, () -> MethodEnumConvertor.convert(null));
    }
}
