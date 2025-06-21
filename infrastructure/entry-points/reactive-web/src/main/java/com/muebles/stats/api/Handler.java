package com.muebles.stats.api;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ProcessStatsUseCase processStatsUseCase;

    @Operation(
        summary = "Procesar estadísticas",
        description = "Endpoint para enviar y procesar estadísticas de muebles",
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Stats.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Estadísticas procesadas correctamente"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Solicitud inválida"
            )
        }
    )
    public Mono<ServerResponse> listenPOSTStats(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Stats.class)
                .flatMap(processStatsUseCase::execute)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
