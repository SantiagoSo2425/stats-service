package com.muebles.stats.usecase.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidHashExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String errorMessage = "Hash inválido";

        // Act
        InvalidHashException exception = new InvalidHashException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldInheritFromRuntimeException() {
        // Arrange & Act
        InvalidHashException exception = new InvalidHashException("Test message");

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}
