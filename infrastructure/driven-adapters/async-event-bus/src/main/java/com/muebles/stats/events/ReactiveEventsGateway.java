package com.muebles.stats.events;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.muebles.stats.model.events.gateways.EventsGateway;
import com.muebles.stats.model.stats.Stats;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import reactor.core.publisher.Mono;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.JsonCloudEventData;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {

    public static final String EVENT_NAME = "event.stats.validated";

    private final DomainEventBus domainEventBus;
    private final ObjectMapper om;

    @Override
    public Mono<Void> emit(Object event) {
        Stats stats = (Stats) event;
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{EVENT_NAME, stats.toString()});
        CloudEvent eventCloudEvent = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://muebles.com/stats"))
                .withType(EVENT_NAME)
                .withTime(OffsetDateTime.now())
                .withData("application/json", JsonCloudEventData.wrap(om.valueToTree(stats)))
                .build();
        return from(domainEventBus.emit(eventCloudEvent));
    }


}
