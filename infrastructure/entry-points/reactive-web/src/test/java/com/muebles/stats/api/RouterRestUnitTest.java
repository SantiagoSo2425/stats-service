package com.muebles.stats.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class RouterRestUnitTest {

    @Test
    void shouldCreateRouterFunction() {
        // Arrange
        RouterRest routerRest = new RouterRest();
        Handler handler = mock(Handler.class);

        // Act
        RouterFunction<ServerResponse> routerFunction = routerRest.routerFunction(handler);

        // Assert
        assertNotNull(routerFunction);
    }
}
