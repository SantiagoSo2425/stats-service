package com.muebles.stats.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import org.reactivecommons.api.domain.DomainEventBus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cloudevents.CloudEvent;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.muebles.stats.model.stats.Stats;

class ReactiveEventsGatewayTest {

    @Mock
    private DomainEventBus domainEventBus;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveEventsGateway gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gateway = new ReactiveEventsGateway(domainEventBus, objectMapper);
        when(domainEventBus.emit(any(CloudEvent.class))).thenReturn(Mono.empty());
    }

    @Test
    void testEmitLogsEvent() {
        Stats event = Stats.builder().timestamp("2024-01-01T00:00:00Z").hash("hashValue").build();
        when(objectMapper.valueToTree(event)).thenReturn(mock(ObjectNode.class));
        gateway.emit(event).block();
        verify(domainEventBus, times(1)).emit(any(CloudEvent.class));
    }

   @Test
    void testEmitConstructsCloudEvent() {
        Stats event = Stats.builder().timestamp("2024-01-01T00:00:00Z").hash("hashValue").build();
        when(objectMapper.valueToTree(event)).thenReturn(mock(ObjectNode.class));
        gateway.emit(event).block();
        ArgumentCaptor<CloudEvent> eventCaptor = ArgumentCaptor.forClass(CloudEvent.class);
        verify(domainEventBus, times(1)).emit(eventCaptor.capture());
        CloudEvent cloudEvent = eventCaptor.getValue();
        assertEquals(ReactiveEventsGateway.EVENT_NAME, cloudEvent.getType());
        assertEquals("https://muebles.com/stats", cloudEvent.getSource().toString());
    }


}
