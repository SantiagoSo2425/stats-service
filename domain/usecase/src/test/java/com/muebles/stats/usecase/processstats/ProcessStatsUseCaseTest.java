package com.muebles.stats.usecase.processstats;

import com.muebles.stats.model.events.gateways.EventsGateway;
import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.StatsRepository;
import com.muebles.stats.usecase.exceptions.InvalidHashException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessStatsUseCaseTest {

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private EventsGateway eventsGateway;

    private ProcessStatsUseCase processStatsUseCase;

    @BeforeEach
    void setUp() {
        processStatsUseCase = new ProcessStatsUseCase(statsRepository, eventsGateway);
    }

    @Test
    void shouldProcessValidStats() {
        // Arrange
        Stats stats = createValidStats();

        when(statsRepository.save(any(Stats.class))).thenReturn(Mono.empty());
        when(eventsGateway.emit(any(Stats.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(processStatsUseCase.execute(stats))
                .verifyComplete();

        verify(statsRepository, times(1)).save(any(Stats.class));
        verify(eventsGateway, times(1)).emit(any(Stats.class));
    }

    @Test
    void shouldRejectStatsWithInvalidHash() {
        // Arrange
        Stats stats = Stats.builder()
                .totalContactoClientes(100)
                .motivoReclamo(20)
                .motivoGarantia(15)
                .motivoDuda(25)
                .motivoCompra(30)
                .motivoFelicitaciones(5)
                .motivoCambio(5)
                .hash("invalidHash")
                .build();

        // Act & Assert
        StepVerifier.create(processStatsUseCase.execute(stats))
                .expectError(InvalidHashException.class)
                .verify();

        verify(statsRepository, never()).save(any(Stats.class));
        verify(eventsGateway, never()).emit(any(Stats.class));
    }

    @Test
    void shouldHandleRepositorySaveError() {
        // Arrange
        Stats stats = createValidStats();

        when(statsRepository.save(any(Stats.class)))
                .thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(processStatsUseCase.execute(stats))
                .expectError(RuntimeException.class)
                .verify();

        verify(statsRepository, times(1)).save(any(Stats.class));
        verify(eventsGateway, never()).emit(any(Stats.class));
    }

    @Test
    void shouldHandleEventEmissionError() {
        // Arrange
        Stats stats = createValidStats();

        when(statsRepository.save(any(Stats.class))).thenReturn(Mono.empty());
        when(eventsGateway.emit(any(Stats.class)))
                .thenReturn(Mono.error(new RuntimeException("Event bus error")));

        // Act & Assert
        StepVerifier.create(processStatsUseCase.execute(stats))
                .expectError(RuntimeException.class)
                .verify();

        verify(statsRepository, times(1)).save(any(Stats.class));
        verify(eventsGateway, times(1)).emit(any(Stats.class));
    }

    private Stats createValidStats() {
        int totalContactoClientes = 100;
        int motivoReclamo = 20;
        int motivoGarantia = 15;
        int motivoDuda = 25;
        int motivoCompra = 30;
        int motivoFelicitaciones = 5;
        int motivoCambio = 5;

        String data = String.format("%d,%d,%d,%d,%d,%d,%d",
                totalContactoClientes, motivoReclamo, motivoGarantia, motivoDuda,
                motivoCompra, motivoFelicitaciones, motivoCambio);

        String validHash = calculateMD5(data);

        return Stats.builder()
                .totalContactoClientes(totalContactoClientes)
                .motivoReclamo(motivoReclamo)
                .motivoGarantia(motivoGarantia)
                .motivoDuda(motivoDuda)
                .motivoCompra(motivoCompra)
                .motivoFelicitaciones(motivoFelicitaciones)
                .motivoCambio(motivoCambio)
                .hash(validHash)
                .build();
    }

    private String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular el hash MD5", e);
        }
    }
}
