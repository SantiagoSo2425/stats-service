package com.muebles.stats.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class UseCasesConfigTest {

    @Test
    void shouldHaveCorrectAnnotations() {
        // Verificar que la clase tiene las anotaciones correctas
        assertTrue(UseCasesConfig.class.isAnnotationPresent(Configuration.class));
        assertTrue(UseCasesConfig.class.isAnnotationPresent(ComponentScan.class));

        // Verificar la configuración del ComponentScan
        ComponentScan componentScan = UseCasesConfig.class.getAnnotation(ComponentScan.class);
        assertEquals("com.muebles.stats.usecase", componentScan.basePackages()[0]);
        assertEquals(ComponentScan.Filter.class, componentScan.includeFilters()[0].annotationType());
        assertFalse(componentScan.useDefaultFilters());
    }
}
