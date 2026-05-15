package com.example.ratelimiter.controller;

import com.example.ratelimiter.api.RatelimiterApi;
import com.example.ratelimiter.api.model.*;
import com.example.ratelimiter.dal.RateLimitRule;
import com.example.ratelimiter.service.RateLimitRuleService;
import com.example.ratelimiter.service.RateLimitValidator;
import com.example.ratelimiter.utils.DurationConvertor;
import com.example.ratelimiter.utils.MethodEnumConvertor;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController implements RatelimiterApi {

    RateLimitRuleService rateLimitRuleService;
    RateLimitValidator rateLimitValidator;

    public RateLimiterController(RateLimitRuleService rateLimitRuleService) {
        this.rateLimitRuleService = rateLimitRuleService;
    }

    @Override
    public ResponseEntity<CreateRateLimitRuleResponse> createRateLimitRule(
            CreateRateLimitRuleRequest request) {
        String id =
                rateLimitRuleService.createRateLimitRule(
                        request.getApiSignature(),
                        DurationConvertor.convert(request.getDuration()),
                        request.getLimit());
        return ResponseEntity.status(201)
                .body(CreateRateLimitRuleResponse.builder().id(id).build());
    }

    public ResponseEntity<GetRateLimitRuleResponse> getRateLimitRuleById(String id) {
        Optional<RateLimitRule> rateLimit = rateLimitRuleService.getRateLimitRule(id);
        if (rateLimit.isEmpty()) return ResponseEntity.notFound().build();
        return getRateLimitResponseEntity(rateLimit.get());
    }

    @Override
    public ResponseEntity<RateLimitValidateResponse> validateRateLimit(
            String service, String uri, String method) {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(service)
                        .uri(uri)
                        .method(MethodEnumConvertor.convert(method))
                        .build();
        return ResponseEntity.ok(rateLimitValidator.validateRateLimit(apiSignature));
    }

    @Override
    public ResponseEntity<GetRateLimitRuleResponse> getRateLimitRule(
            String service, String uri, String method) {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(service)
                        .uri(uri)
                        .method(MethodEnumConvertor.convert(method))
                        .build();
        Optional<RateLimitRule> rateLimit = rateLimitRuleService.getRateLimitRule(apiSignature);
        if (rateLimit.isEmpty()) return ResponseEntity.notFound().build();
        return getRateLimitResponseEntity(rateLimit.get());
    }

    private static ResponseEntity<GetRateLimitRuleResponse> getRateLimitResponseEntity(
            RateLimitRule rateLimitRule) {
        return ResponseEntity.ok(
                GetRateLimitRuleResponse.builder()
                        .id(rateLimitRule.getId())
                        .apiSignature(rateLimitRule.getApiSignature())
                        .duration(
                                RateLimitRuleDuration.builder()
                                        .value(rateLimitRule.getDuration().getSeconds())
                                        .unit(RateLimitRuleDuration.UnitEnum.SECONDS)
                                        .build())
                        .limit(rateLimitRule.getLimit())
                        .build());
    }
}
