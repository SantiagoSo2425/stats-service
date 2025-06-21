package com.muebles.stats.api.doc;

import com.muebles.stats.model.stats.Stats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Este controlador es solo para la documentación Swagger.
 * La implementación real se hace a través de router functions.
 */
@RestController
@Tag(name = "Estadísticas", description = "API  muebles")
public class StatsDocController {

    @Operation(
        summary = "Procesar estadísticas",
        description = "Endpoint para enviar y procesar estadísticas",
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
    @PostMapping(
        path = "/stats",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void processStats(@RequestBody Stats stats) {
        // Esta implementación nunca se ejecuta realmente.
        // Es solo para que Swagger pueda documentar el endpoint.
        throw new UnsupportedOperationException("Este método solo existe para la documentación Swagger");
    }
}
