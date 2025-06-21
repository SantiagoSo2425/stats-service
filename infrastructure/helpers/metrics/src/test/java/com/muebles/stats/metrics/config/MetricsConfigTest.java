package com.muebles.stats.metrics.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import software.amazon.awssdk.metrics.MetricPublisher;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MetricsConfig.class)
class MetricsConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void shouldCreateMetricPublisherBean() {
        // Arrange & Act
        MetricPublisher publisher = context.getBean(MetricPublisher.class);

        // Assert
        assertNotNull(publisher, "El bean MetricPublisher debe existir");
    }
}
