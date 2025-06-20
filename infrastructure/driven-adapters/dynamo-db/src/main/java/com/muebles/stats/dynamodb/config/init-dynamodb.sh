#!/bin/sh

export AWS_ACCESS_KEY_ID="dummy"
export AWS_SECRET_ACCESS_KEY="dummy"

TABLE_NAME="StatsTable"

aws dynamodb create-table \
  --table-name $TABLE_NAME \
  --attribute-definitions AttributeName=timestamp,AttributeType=S AttributeName=hash,AttributeType=S \
  --key-schema AttributeName=timestamp,KeyType=HASH \
  --global-secondary-indexes \
    '[{"IndexName": "secondary_index","KeySchema":[{"AttributeName":"hash","KeyType":"HASH"}],"Projection":{"ProjectionType":"ALL"},"ProvisionedThroughput":{"ReadCapacityUnits":1,"WriteCapacityUnits":1}}]' \
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
  --endpoint-url http://dynamodb-local:8000 \
  --region us-west-2

echo "Tabla $TABLE_NAME creada en DynamoDB Local con índice secundario secondary_index."