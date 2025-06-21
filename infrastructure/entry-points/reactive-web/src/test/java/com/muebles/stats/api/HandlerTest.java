package com.muebles.stats.api;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.usecase.exceptions.InvalidHashException;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private ProcessStatsUseCase processStatsUseCase;

    @InjectMocks
    private Handler handler;

    @Mock
    private ServerRequest serverRequest;

    @BeforeEach
    void setUp() {
        // No es necesario inicializar nada adicional aquí porque usamos @InjectMocks
    }

    @Test
    void shouldReturnOkForSuccessfulStatsProcessing() {
        // Arrange
        Stats stats = createValidStats();
        when(serverRequest.bodyToMono(Stats.class)).thenReturn(Mono.just(stats));
        when(processStatsUseCase.execute(any(Stats.class))).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> response = handler.listenPOSTStats(serverRequest);

        // Assert
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                    serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void shouldReturnBadRequestWhenHashIsInvalid() {
        // Arrange
        Stats stats = createValidStats();
        when(serverRequest.bodyToMono(Stats.class)).thenReturn(Mono.just(stats));
        when(processStatsUseCase.execute(any(Stats.class)))
                .thenReturn(Mono.error(new InvalidHashException("Hash inválido")));

        // Act
        Mono<ServerResponse> response = handler.listenPOSTStats(serverRequest);

        // Assert
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                    serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void shouldHandleServerErrorGracefully() {
        // Arrange
        Stats stats = createValidStats();
        when(serverRequest.bodyToMono(Stats.class)).thenReturn(Mono.just(stats));
        when(processStatsUseCase.execute(any(Stats.class)))
                .thenReturn(Mono.error(new RuntimeException("Error interno")));

        // Act
        Mono<ServerResponse> response = handler.listenPOSTStats(serverRequest);

        // Assert
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                    serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    private Stats createValidStats() {
        return Stats.builder()
                .totalContactoClientes(100)
                .motivoReclamo(20)
                .motivoGarantia(15)
                .motivoDuda(25)
                .motivoCompra(30)
                .motivoFelicitaciones(5)
                .motivoCambio(5)
                .hash("validHash")
                .build();
    }
}
