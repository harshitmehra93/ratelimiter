package com.example.ratelimiter.controller;

import com.example.ratelimiter.api.RatelimiterApi;
import com.example.ratelimiter.api.model.*;
import com.example.ratelimiter.dal.RateLimit;
import com.example.ratelimiter.service.RateLimiterService;
import com.example.ratelimiter.utils.DurationConvertor;
import com.example.ratelimiter.utils.MethodEnumConvertor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController implements RatelimiterApi {

    RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public ResponseEntity<CreateRateLimitResponse> createRateLimiter(
            CreateRateLimitRequest request) {
        String id =
                rateLimiterService.createRateLimiter(
                        request.getApiSignature(),
                        DurationConvertor.convert(request.getDuration()),
                        request.getLimit());
        return ResponseEntity.status(201).body(CreateRateLimitResponse.builder().id(id).build());
    }

    @Override
    public ResponseEntity<GetRateLimitResponse> getRateLimiterById(String id) {
        RateLimit rateLimit = rateLimiterService.getRateLimit(id);
        return getRateLimitResponseEntity(rateLimit);
    }

    @Override
    public ResponseEntity<RateLimitValidateResponse> validateRateLimit(String service, String uri, String method) {
        ApiSignature apiSignature = ApiSignature.builder()
                .service(service)
                .uri(uri)
                .method(MethodEnumConvertor.convert(method))
                .build();
        RateLimit rateLimit = rateLimiterService.getRateLimit(apiSignature);

        return null;
    }

    @Override
    public ResponseEntity<GetRateLimitResponse> getRateLimiter(
            String service, String uri, String method) {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(service)
                        .uri(uri)
                        .method(MethodEnumConvertor.convert(method))
                        .build();
        RateLimit rateLimit = rateLimiterService.getRateLimit(apiSignature);
        return getRateLimitResponseEntity(rateLimit);
    }

    private static ResponseEntity<GetRateLimitResponse> getRateLimitResponseEntity(
            RateLimit rateLimit) {
        return ResponseEntity.ok(
                GetRateLimitResponse.builder()
                        .id(rateLimit.getId())
                        .apiSignature(rateLimit.getApiSignature())
                        .duration(
                                RateLimitDuration.builder()
                                        .value(rateLimit.getDuration().getSeconds())
                                        .unit(RateLimitDuration.UnitEnum.SECONDS)
                                        .build())
                        .limit(rateLimit.getLimit())
                        .build());
    }
}
