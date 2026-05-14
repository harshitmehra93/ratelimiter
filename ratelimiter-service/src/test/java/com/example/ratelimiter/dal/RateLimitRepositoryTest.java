package com.example.ratelimiter.dal;

import static org.junit.jupiter.api.Assertions.*;

import com.example.ratelimiter.model.CreateRateLimitDetails;
import com.example.ratelimiter.model.UpdateRateLimitDetails;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateLimitRepositoryTest {

    public static final String SERVICE = "service";
    public static final String URI = "/home";
    public static final String GET = "GET";
    public static final Duration SIXTY_SECONDS = Duration.ofSeconds(60);
    public static final int LIMIT = 100;

    private RateLimitRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRateLimitRepository();
    }

    @Test
    void createRateLimit() {
        var rateLimit =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .service(SERVICE)
                                .uri(URI)
                                .method(GET)
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());
        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getService());
        assertEquals(URI, rateLimit.getUri());
        assertEquals(GET, rateLimit.getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void getRateLimit() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .service(SERVICE)
                                .uri(URI)
                                .method(GET)
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());

        var rateLimit = repository.getRateLimit(original.getId());

        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getService());
        assertEquals(URI, rateLimit.getUri());
        assertEquals(GET, rateLimit.getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void getWithServiceMethodUri() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .service(SERVICE)
                                .uri(URI)
                                .method(GET)
                                .duration(SIXTY_SECONDS)
                                .limit(LIMIT)
                                .build());

        var rateLimit =
                repository.getRateLimit(
                        original.getService(), original.getUri(), original.getMethod());
        assertNotNull(rateLimit);
        assertNotNull(rateLimit.getId());
        assertEquals(SERVICE, rateLimit.getService());
        assertEquals(URI, rateLimit.getUri());
        assertEquals(GET, rateLimit.getMethod());
        assertEquals(SIXTY_SECONDS, rateLimit.getDuration());
        assertEquals(LIMIT, rateLimit.getLimit());
    }

    @Test
    void updateRateLimit() {
        var original =
                repository.createRateLimit(
                        CreateRateLimitDetails.builder()
                                .service(SERVICE)
                                .uri(URI)
                                .method(GET)
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
        assertEquals(SERVICE, rateLimit.getService());
        assertEquals(URI, rateLimit.getUri());
        assertEquals(GET, rateLimit.getMethod());
        assertEquals(Duration.ofSeconds(120), rateLimit.getDuration());
        assertEquals(200, rateLimit.getLimit());
    }
}
