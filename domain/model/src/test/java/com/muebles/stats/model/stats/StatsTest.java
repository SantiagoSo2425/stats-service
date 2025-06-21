package com.muebles.stats.model.stats;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    @Test
    void shouldCreateStatsWithBuilder() {
        // Arrange & Act
        Stats stats = Stats.builder()
                .totalContactoClientes(100)
                .motivoReclamo(20)
                .motivoGarantia(15)
                .motivoDuda(25)
                .motivoCompra(30)
                .motivoFelicitaciones(5)
                .motivoCambio(5)
                .hash("testHash")
                .timestamp("2025-06-20T12:00:00Z")
                .build();

        // Assert
        assertEquals(100, stats.getTotalContactoClientes());
        assertEquals(20, stats.getMotivoReclamo());
        assertEquals(15, stats.getMotivoGarantia());
        assertEquals(25, stats.getMotivoDuda());
        assertEquals(30, stats.getMotivoCompra());
        assertEquals(5, stats.getMotivoFelicitaciones());
        assertEquals(5, stats.getMotivoCambio());
        assertEquals("testHash", stats.getHash());
        assertEquals("2025-06-20T12:00:00Z", stats.getTimestamp());
    }

    @Test
    void shouldCreateStatsWithNoArgsConstructor() {
        // Arrange & Act
        Stats stats = new Stats();

        // Assert
        assertEquals(0, stats.getTotalContactoClientes());
        assertEquals(0, stats.getMotivoReclamo());
        assertEquals(0, stats.getMotivoGarantia());
        assertEquals(0, stats.getMotivoDuda());
        assertEquals(0, stats.getMotivoCompra());
        assertEquals(0, stats.getMotivoFelicitaciones());
        assertEquals(0, stats.getMotivoCambio());
        assertNull(stats.getHash());
        assertNull(stats.getTimestamp());
    }

    @Test
    void shouldCreateStatsWithAllArgsConstructor() {
        // Arrange & Act
        Stats stats = new Stats(100, 20, 15, 25, 30, 5, 5, "testHash", "2025-06-20T12:00:00Z");

        // Assert
        assertEquals(100, stats.getTotalContactoClientes());
        assertEquals(20, stats.getMotivoReclamo());
        assertEquals(15, stats.getMotivoGarantia());
        assertEquals(25, stats.getMotivoDuda());
        assertEquals(30, stats.getMotivoCompra());
        assertEquals(5, stats.getMotivoFelicitaciones());
        assertEquals(5, stats.getMotivoCambio());
        assertEquals("testHash", stats.getHash());
        assertEquals("2025-06-20T12:00:00Z", stats.getTimestamp());
    }

    @Test
    void shouldModifyStatsWithSetters() {
        // Arrange
        Stats stats = new Stats();

        // Act
        stats.setTotalContactoClientes(100);
        stats.setMotivoReclamo(20);
        stats.setMotivoGarantia(15);
        stats.setMotivoDuda(25);
        stats.setMotivoCompra(30);
        stats.setMotivoFelicitaciones(5);
        stats.setMotivoCambio(5);
        stats.setHash("testHash");
        stats.setTimestamp("2025-06-20T12:00:00Z");

        // Assert
        assertEquals(100, stats.getTotalContactoClientes());
        assertEquals(20, stats.getMotivoReclamo());
        assertEquals(15, stats.getMotivoGarantia());
        assertEquals(25, stats.getMotivoDuda());
        assertEquals(30, stats.getMotivoCompra());
        assertEquals(5, stats.getMotivoFelicitaciones());
        assertEquals(5, stats.getMotivoCambio());
        assertEquals("testHash", stats.getHash());
        assertEquals("2025-06-20T12:00:00Z", stats.getTimestamp());
    }

    @Test
    void shouldCloneStatsWithToBuilder() {
        // Arrange
        Stats original = Stats.builder()
                .totalContactoClientes(100)
                .motivoReclamo(20)
                .motivoGarantia(15)
                .motivoDuda(25)
                .motivoCompra(30)
                .motivoFelicitaciones(5)
                .motivoCambio(5)
                .hash("testHash")
                .timestamp("2025-06-20T12:00:00Z")
                .build();

        // Act
        Stats modified = original.toBuilder()
                .totalContactoClientes(200)
                .hash("newHash")
                .build();

        // Assert
        assertEquals(200, modified.getTotalContactoClientes());
        assertEquals(20, modified.getMotivoReclamo());
        assertEquals(15, modified.getMotivoGarantia());
        assertEquals(25, modified.getMotivoDuda());
        assertEquals(30, modified.getMotivoCompra());
        assertEquals(5, modified.getMotivoFelicitaciones());
        assertEquals(5, modified.getMotivoCambio());
        assertEquals("newHash", modified.getHash());
        assertEquals("2025-06-20T12:00:00Z", modified.getTimestamp());

        // Verificar que el original no cambia
        assertEquals(100, original.getTotalContactoClientes());
        assertEquals("testHash", original.getHash());
    }
}
