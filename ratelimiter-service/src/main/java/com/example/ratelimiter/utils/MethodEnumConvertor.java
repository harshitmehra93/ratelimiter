package com.example.ratelimiter.utils;

import com.example.ratelimiter.api.model.ApiSignature;

public class MethodEnumConvertor {

    public static ApiSignature.MethodEnum convert(String method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        return ApiSignature.MethodEnum.fromValue(method.trim().toUpperCase());
    }
}
