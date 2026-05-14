package com.example.ratelimiter.controller;

import com.example.ratelimiter.api.RatelimiterApi;
import com.example.ratelimiter.api.model.CreateRateLimitRequest;
import com.example.ratelimiter.api.model.CreateRateLimitResponse;
import com.example.ratelimiter.api.model.GetRateLimitResponse;
import com.example.ratelimiter.api.model.RateLimitDuration;
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
                        request.getService(),
                        request.getApi(),
                        request.getMethod().toString(),
                        DurationConvertor.convert(request.getDuration()),
                        request.getLimit());
        return ResponseEntity.status(201).body(CreateRateLimitResponse.builder().id(id).build());
    }

    @Override
    public ResponseEntity<GetRateLimitResponse> getRateLimiterById(String id) {
        RateLimit rateLimit = rateLimiterService.getRateLimit(id);
        return getRateLimitResponseResponseEntity(rateLimit);
    }

    @Override
    public ResponseEntity<GetRateLimitResponse> getRateLimiter(
            String service, String uri, String method) {
        RateLimit rateLimit = rateLimiterService.getRateLimit(service, uri, method);
        return getRateLimitResponseResponseEntity(rateLimit);
    }

    private static ResponseEntity<GetRateLimitResponse> getRateLimitResponseResponseEntity(
            RateLimit rateLimit) {
        return ResponseEntity.ok(
                GetRateLimitResponse.builder()
                        .id(rateLimit.getId())
                        .service(rateLimit.getService())
                        .api(rateLimit.getUri())
                        .method(MethodEnumConvertor.convert(rateLimit.getMethod()))
                        .duration(
                                RateLimitDuration.builder()
                                        .value(rateLimit.getDuration().getSeconds())
                                        .unit(RateLimitDuration.UnitEnum.SECONDS)
                                        .build())
                        .limit(rateLimit.getLimit())
                        .build());
    }
}
