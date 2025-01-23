package com.verix.landing.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.DATE;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.STRING;

public class LandingTableSchema {

    private final TableSchema schema;

    public LandingTableSchema(TableSchema schema) {
        this.schema = schema;
    }

    public TableSchema create(){
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema().setName("component_id").setType(STRING.name()),
                                new TableFieldSchema().setName("apm_code").setType("STRING"),
                                new TableFieldSchema().setName("app_name").setType("STRING"),
                                new TableFieldSchema().setName("vendor").setType("STRING"),
                                new TableFieldSchema().setName("sw_type").setType("STRING"),
                                new TableFieldSchema().setName("sw_name").setType("STRING"),
                                new TableFieldSchema().setName("sw_id").setType("Integer")));
    }
}
