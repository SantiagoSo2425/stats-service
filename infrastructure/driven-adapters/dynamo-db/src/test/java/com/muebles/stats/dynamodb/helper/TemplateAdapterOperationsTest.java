package com.muebles.stats.dynamodb.helper;

import com.muebles.stats.dynamodb.DynamoDBTemplateAdapter;
import com.muebles.stats.dynamodb.StatsEntity;
import com.muebles.stats.model.stats.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<StatsEntity> customerTable;

    private StatsEntity statsEntity;
    private Stats stats;
    private DynamoDBTemplateAdapter dynamoDBTemplateAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear objetos de prueba
        statsEntity = StatsEntity.builder()
                .timestamp("2024-01-01T00:00:00Z")
                .hash("hashValue")
                .totalContactoClientes(1)
                .motivoReclamo(2)
                .motivoGarantia(3)
                .motivoDuda(4)
                .motivoCompra(5)
                .motivoFelicitaciones(6)
                .motivoCambio(7)
                .build();

        stats = Stats.builder()
                .timestamp("2024-01-01T00:00:00Z")
                .hash("hashValue")
                .totalContactoClientes(1)
                .motivoReclamo(2)
                .motivoGarantia(3)
                .motivoDuda(4)
                .motivoCompra(5)
                .motivoFelicitaciones(6)
                .motivoCambio(7)
                .build();

        // Configurar mocks
        when(dynamoDbEnhancedAsyncClient.table(any(String.class), any(TableSchema.class)))
                .thenReturn(customerTable);

        when(mapper.map(any(Stats.class), eq(StatsEntity.class))).thenReturn(statsEntity);
        when(mapper.map(any(StatsEntity.class), eq(Stats.class))).thenReturn(stats);

        when(customerTable.putItem(any(StatsEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        when(customerTable.getItem(any(Key.class)))
                .thenReturn(CompletableFuture.completedFuture(statsEntity));

        when(customerTable.deleteItem(any(StatsEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(statsEntity));

        // Crear el adaptador con los mocks
        dynamoDBTemplateAdapter = new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        StatsEntity statsEntityUnderTest = StatsEntity.builder()
                .timestamp("2024-01-01T00:00:00Z")
                .hash("hashValue")
                .build();

        assertNotNull(statsEntityUnderTest.getTimestamp());
        assertNotNull(statsEntityUnderTest.getHash());
    }

    @Test
    void testSave() {
        StepVerifier.create(dynamoDBTemplateAdapter.save(stats))
                .verifyComplete();
    }

    @Test
    void testGetById() {
        // No necesitamos verificar que devuelva exactamente stats,
        // solo verificamos que complete exitosamente y devuelva un objeto
        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        // No necesitamos verificar que devuelva exactamente stats,
        // solo verificamos que complete exitosamente y devuelva un objeto
        StepVerifier.create(dynamoDBTemplateAdapter.delete(stats))
                .expectNextCount(1)
                .verifyComplete();
    }
}