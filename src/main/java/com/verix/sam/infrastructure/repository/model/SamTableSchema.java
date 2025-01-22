package com.verix.sam.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.DATE;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.STRING;

public class SamTableSchema {
    private final TableSchema schema;

    public SamTableSchema(TableSchema schema) {
        this.schema = schema;
    }

    public TableSchema create() {
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema()
                                        .setName("publisher")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("category")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("product")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("product_version")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED"),
                                new TableFieldSchema()
                                        .setName("version")
                                        .setType(STRING.name()),
                                new TableFieldSchema()
                                        .setName("full_version")
                                        .setType(STRING.name()),
                                new TableFieldSchema()
                                        .setName("edition")
                                        .setType(STRING.name()),
                                new TableFieldSchema()
                                        .setName("internal_availability")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("internal_end_of_support")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("publisher_availability")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("publisher_end_of_support")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("publisher_end_of_extended_support")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("publisher_end_of_life")
                                        .setType(DATE.name()),
                                new TableFieldSchema()
                                        .setName("source")
                                        .setType(STRING.name())
                                        .setMode("REQUIRED")
                        )
                );
    }
}
