package com.muebles.stats.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.muebles.stats.model.stats.Stats;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperation(
        path = "/stats",
        method = RequestMethod.POST,
        operation = @Operation(
            summary = "Procesar estadísticas",
            description = "Endpoint para enviar y procesar estadísticas de muebles",
            requestBody = @RequestBody(
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
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
    )
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/stats"), handler::listenPOSTStats);
    }
}
