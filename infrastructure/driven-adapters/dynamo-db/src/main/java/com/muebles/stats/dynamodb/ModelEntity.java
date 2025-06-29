package com.muebles.stats.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class ModelEntity {

    private String id;
    private String atr1;

    public ModelEntity() {
    }

    public ModelEntity(String id, String atr1) {
        this.id = id;
        this.atr1 = atr1;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("atr1")
    public String getAtr1() {
        return atr1;
    }

    public void setAtr1(String atr1) {
        this.atr1 = atr1;
    }
}
