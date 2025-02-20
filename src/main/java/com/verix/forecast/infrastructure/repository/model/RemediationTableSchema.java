package com.verix.forecast.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.DATE;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.STRING;

public class RemediationTableSchema {
    private final TableSchema schema;

    public RemediationTableSchema(TableSchema schema) {
        this.schema = schema;
    }

    public TableSchema create() {
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema()
                                        .setName("strategy")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("country_code")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("apm_code")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("component")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("version")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("action")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("new_version")
                                        .setType(STRING.name()),
                                new TableFieldSchema()
                                        .setName("delivery_date")
                                        .setType(DATE.name())
                                        .setMode("REQUIRED")
                        )
                );
    }
}
