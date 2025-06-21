package com.muebles.stats.metrics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.metrics.MetricPublisher;

@Configuration
public class MetricsConfig {

    @Bean
    public MetricPublisher metricPublisher() {
        // Implementación simple que no hace nada, solo para cumplir con la inyección de dependencias
        return new NoOpMetricPublisher();
    }

    /**
     * Implementación vacía de MetricPublisher que no realiza ninguna acción.
     * Se utiliza solo para satisfacer la dependencia requerida por DynamoDB.
     */
    private static class NoOpMetricPublisher implements MetricPublisher {
        @Override
        public void publish(software.amazon.awssdk.metrics.MetricCollection metricCollection) {
            // No realizar ninguna acción
        }

        @Override
        public void close() {
            // No realizar ninguna acción
        }
    }
}
