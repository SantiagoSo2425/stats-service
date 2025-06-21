package com.muebles.stats.dynamodb;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelEntityTest {

    @Test
    void shouldCreateModelEntityWithConstructor() {
        // Arrange & Act
        ModelEntity entity = new ModelEntity("testId", "testAtr1");

        // Assert
        assertEquals("testId", entity.getId());
        assertEquals("testAtr1", entity.getAtr1());
    }

    @Test
    void shouldCreateModelEntityWithDefaultConstructorAndSetters() {
        // Arrange
        ModelEntity entity = new ModelEntity();

        // Act
        entity.setId("testId");
        entity.setAtr1("testAtr1");

        // Assert
        assertEquals("testId", entity.getId());
        assertEquals("testAtr1", entity.getAtr1());
    }
}
