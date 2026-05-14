package com.example.ratelimiter.utils;

import com.example.ratelimiter.api.model.GetRateLimitResponse;

public class MethodEnumConvertor {

    public static GetRateLimitResponse.MethodEnum convert(String method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        return GetRateLimitResponse.MethodEnum.fromValue(method.trim().toUpperCase());
    }
}
