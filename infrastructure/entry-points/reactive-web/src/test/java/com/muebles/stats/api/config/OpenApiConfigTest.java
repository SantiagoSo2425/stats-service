package com.muebles.stats.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OpenApiConfig.class)
class OpenApiConfigTest {

    @Autowired
    private OpenApiConfig openApiConfig;

    @Test
    void shouldCreateOpenAPIBean() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(openAPI, "El bean OpenAPI debe existir");
        assertNotNull(openAPI.getInfo(), "OpenAPI debe tener información");
        assertEquals("API de Estadísticas de Muebles", openAPI.getInfo().getTitle(), "El título debe ser correcto");
        assertEquals("1.0", openAPI.getInfo().getVersion(), "La versión debe ser correcta");
        assertNotNull(openAPI.getInfo().getContact(), "Debe incluir información de contacto");
        assertNotNull(openAPI.getInfo().getLicense(), "Debe incluir información de licencia");
    }
}
