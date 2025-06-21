package com.muebles.stats.api.doc;

import com.muebles.stats.model.stats.Stats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = StatsDocController.class)
class StatsDocControllerTest {

    @Autowired
    private StatsDocController controller;

    @Test
    void shouldThrowExceptionWhenProcessStatsIsCalled() {
        // Arrange
        Stats stats = new Stats();

        // Act & Assert
        assertNotNull(controller, "El controlador debe existir");
        assertThrows(UnsupportedOperationException.class,
                     () -> controller.processStats(stats),
                     "Debe lanzar excepción ya que es solo para documentación");
    }
}
