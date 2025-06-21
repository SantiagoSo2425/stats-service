package com.muebles.stats.usecase.processstats;

import com.muebles.stats.model.events.gateways.EventsGateway;
import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.StatsRepository;
import com.muebles.stats.usecase.exceptions.InvalidHashException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Caso de uso para procesar estadísticas de contacto con clientes.
 * Valida el hash MD5 y, si es correcto, continúa con el flujo de persistencia y publicación.
 */
@RequiredArgsConstructor
public class ProcessStatsUseCase {

    private static final Logger log = Logger.getLogger(ProcessStatsUseCase.class.getName());

    private final StatsRepository statsRepository;
    private final EventsGateway eventsGateway;

    public Mono<Void> execute(Stats stats) {
        log.info("Iniciando procesamiento de estadísticas: " + stats);

        return validateHash(stats)
                .flatMap(this::saveStats)
                .flatMap(this::emitEvent)
                .doOnError(e -> log.log(Level.SEVERE, "Error en el procesamiento: " + e.getMessage()));
    }

    private Mono<Stats> validateHash(Stats stats) {
        return Mono.fromCallable(() -> {
            log.info("Calculando hash para validación");

            String data = String.format("%d,%d,%d,%d,%d,%d,%d",
                    stats.getTotalContactoClientes(),
                    stats.getMotivoReclamo(),
                    stats.getMotivoGarantia(),
                    stats.getMotivoDuda(),
                    stats.getMotivoCompra(),
                    stats.getMotivoFelicitaciones(),
                    stats.getMotivoCambio());

            String calculatedHash = calculateMD5(data);

            // Logs de depuración
           // log.info("Datos para hash: '" + data + "'");
            //log.info("Hash calculado: " + calculatedHash);
            //log.info("Hash recibido: " + stats.getHash());

            if (!calculatedHash.equalsIgnoreCase(stats.getHash())) {
                log.log(Level.SEVERE, "Hash inválido. Calculado: {0}, Recibido: {1}", new Object[]{calculatedHash, stats.getHash()});
                throw new InvalidHashException("El hash proporcionado no es válido.");
            }

           // log.info("Hash validado correctamente");

            // Asignar timestamp aquí para asegurar que tenga valor
            String timestamp = Instant.now().toString();
            log.info("Asignando timestamp: " + timestamp);

            // Crear un nuevo objeto Stats con el timestamp asignado para mantener inmutabilidad
            Stats statsWithTimestamp = stats.toBuilder()
                    .timestamp(timestamp)
                    .build();

            return statsWithTimestamp;
        });
    }

    private Mono<Stats> saveStats(Stats stats) {
        return statsRepository.save(stats)
                .doOnSuccess(v -> log.info("Estadística guardada correctamente"))
                .thenReturn(stats);
    }

    private Mono<Void> emitEvent(Stats stats) {
        return eventsGateway.emit(stats)
                .doOnSuccess(v -> log.info("Evento emitido correctamente"));
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
            log.log(Level.SEVERE, "Error al calcular el hash MD5", e);
            throw new RuntimeException("Error al calcular el hash MD5", e);
        }
    }
}
