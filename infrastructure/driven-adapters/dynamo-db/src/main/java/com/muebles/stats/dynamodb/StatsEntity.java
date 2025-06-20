package com.muebles.stats.dynamodb;

import com.muebles.stats.model.stats.Stats;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsEntity {

    private String timestamp;
    private int totalContactoClientes;
    private int motivoReclamo;
    private int motivoGarantia;
    private int motivoDuda;
    private int motivoCompra;
    private int motivoFelicitaciones;
    private int motivoCambio;
    private String hash;

    @DynamoDbPartitionKey
    public String getTimestamp() {
        return timestamp;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "secondary_index")
    public String getHash() {
        return hash;
    }

    public static StatsEntity fromDomain(Stats stats) {
        return StatsEntity.builder()
                .timestamp(stats.getTimestamp())
                .totalContactoClientes(stats.getTotalContactoClientes())
                .motivoReclamo(stats.getMotivoReclamo())
                .motivoGarantia(stats.getMotivoGarantia())
                .motivoDuda(stats.getMotivoDuda())
                .motivoCompra(stats.getMotivoCompra())
                .motivoFelicitaciones(stats.getMotivoFelicitaciones())
                .motivoCambio(stats.getMotivoCambio())
                .hash(stats.getHash())
                .build();
    }

    public Stats toDomain() {
        return Stats.builder()
                .timestamp(this.timestamp)
                .totalContactoClientes(this.totalContactoClientes)
                .motivoReclamo(this.motivoReclamo)
                .motivoGarantia(this.motivoGarantia)
                .motivoDuda(this.motivoDuda)
                .motivoCompra(this.motivoCompra)
                .motivoFelicitaciones(this.motivoFelicitaciones)
                .motivoCambio(this.motivoCambio)
                .hash(this.hash)
                .build();
    }
}
