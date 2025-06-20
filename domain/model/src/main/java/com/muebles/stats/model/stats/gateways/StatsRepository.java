package com.muebles.stats.model.stats.gateways;

import com.muebles.stats.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface StatsRepository {
    /**
      * Guarda una estadística en el sistema de persistencia.
      * @param stats objeto con los datos a guardar
      * @return Mono<Void> indicando éxito o error
     */
    Mono<Void> save(Stats stats);
}
