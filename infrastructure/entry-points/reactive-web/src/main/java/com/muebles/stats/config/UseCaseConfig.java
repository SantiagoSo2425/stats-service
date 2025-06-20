package com.muebles.stats.config;

import com.muebles.stats.model.events.gateways.EventsGateway;
import com.muebles.stats.model.stats.gateways.StatsRepository;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public ProcessStatsUseCase processStatsUseCase(StatsRepository statsRepository, EventsGateway eventsGateway) {
        return new ProcessStatsUseCase(statsRepository, eventsGateway);
    }
}

