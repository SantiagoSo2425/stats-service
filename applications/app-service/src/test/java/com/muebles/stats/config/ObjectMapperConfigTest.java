package com.muebles.stats.config;

import org.junit.jupiter.api.Test;
import org.reactivecommons.utils.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectMapperConfigTest {

    @Test
    void shouldCreateObjectMapper() {
        // Arrange
        ObjectMapperConfig config = new ObjectMapperConfig();

        // Act
        ObjectMapper mapper = config.objectMapper();

        // Assert
        assertNotNull(mapper);
    }
}
