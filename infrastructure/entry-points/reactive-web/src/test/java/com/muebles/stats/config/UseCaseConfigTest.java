package com.muebles.stats.config;

import com.muebles.stats.model.events.gateways.EventsGateway;
import com.muebles.stats.model.stats.gateways.StatsRepository;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UseCaseConfigTest {

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private EventsGateway eventsGateway;

    @Test
    void shouldCreateProcessStatsUseCase() {
        // Arrange
        UseCaseConfig config = new UseCaseConfig();

        // Act
        ProcessStatsUseCase useCase = config.processStatsUseCase(statsRepository, eventsGateway);

        // Assert
        assertNotNull(useCase);
    }
}
