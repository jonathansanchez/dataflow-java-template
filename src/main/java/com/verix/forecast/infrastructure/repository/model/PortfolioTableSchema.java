package com.verix.forecast.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.*;

public class PortfolioTableSchema {
    private final TableSchema schema;

    public PortfolioTableSchema(TableSchema schema) {
        this.schema = schema;
    }

    public TableSchema create() {
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema()
                                        .setName("country")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("portfolio_date")
                                        .setType(DATE.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("expired")
                                        .setType(INT64.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("expired_kri")
                                        .setType(NUMERIC.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("expiring")
                                        .setType(INT64.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("expiring_kri")
                                        .setType(NUMERIC.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("total")
                                        .setType(INT64.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("created_at")
                                        .setType(DATE.name())
                                        .setMode("REQUIRED")
                        )
                );
    }
}
