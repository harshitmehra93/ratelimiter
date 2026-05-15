package com.example.ratelimiter.dal;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ratelimiter.api.model.ApiSignature;
import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateLimitRuleRepositoryTest {

    public static final String SERVICE = "service";
    public static final String URI = "/home";
    public static final Duration SIXTY_SECONDS = Duration.ofSeconds(60);
    public static final int LIMIT = 100;

    private RateLimitRuleRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRateLimitRuleRepository();
    }

    @Test
    void createRateLimit() {
        var rateLimit =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(
                                        ApiSignature.builder()
                                                .service(SERVICE)
                                                .uri(URI)
                                                .method(ApiSignature.MethodEnum.GET)
                                                .build())
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());
        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void getRateLimit() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(
                                        ApiSignature.builder()
                                                .service(SERVICE)
                                                .uri(URI)
                                                .method(ApiSignature.MethodEnum.GET)
                                                .build())
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());

        var rateLimit = repository.getRateLimit(original.getId()).get();

        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void getWithServiceMethodUri() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(
                                        ApiSignature.builder()
                                                .service(SERVICE)
                                                .uri(URI)
                                                .method(ApiSignature.MethodEnum.GET)
                                                .build())
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());

        var rateLimit = repository.getRateLimit(original.getApiSignature()).get();
        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void updateRateLimit() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .apiSignature(
                                        ApiSignature.builder()
                                                .service(SERVICE)
                                                .uri(URI)
                                                .method(ApiSignature.MethodEnum.GET)
                                                .build())
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());
        var rateLimit =
                repository.updateRateLimit(
                        UpdateRateLimitDetails.builder()
                                .id(original.getId())
                                .duration(Duration.ofSeconds(120))
                                .limit(200)
                                .build());

        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getApiSignature().getService());
        assertEquals(URI, rateLimit.getApiSignature().getUri());
        assertEquals(ApiSignature.MethodEnum.GET, rateLimit.getApiSignature().getMethod());
        assertEquals(Duration.ofSeconds(120), rateLimit.getDuration());
        assertEquals(200, rateLimit.getLimit());
    }

    @Test
    void getRateLimitByID_doesNotExist_throws() {
        assertTrue(repository.getRateLimit("ID").isEmpty());
    }

    @Test
    void getRateLimitBySignature_doesNotExist_throws() {
        ApiSignature apiSignature =
                ApiSignature.builder()
                        .service(SERVICE)
                        .uri(URI)
                        .method(ApiSignature.MethodEnum.GET)
                        .build();
        assertTrue(repository.getRateLimit(apiSignature).isEmpty());
    }
}
