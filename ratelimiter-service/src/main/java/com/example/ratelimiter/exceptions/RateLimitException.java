package com.example.ratelimiter.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RateLimitException extends RuntimeException {
    public RateLimitException(String message) {
        super(message);
    }
}
