package com.muebles.stats.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {

    @Test
    void shouldCreateCorsWebFilter() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        String origins = "http://localhost:4200,https://example.com";

        // Act
        CorsWebFilter filter = corsConfig.corsWebFilter(origins);

        // Assert
        assertNotNull(filter);
    }
}
