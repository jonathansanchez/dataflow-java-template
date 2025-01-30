package com.verix.apm.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.DATE;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.STRING;
import static java.sql.JDBCType.BOOLEAN;

/*
- Define el esquema
 */
public class ApmTableSchema {
    private final TableSchema schema;

    public ApmTableSchema(TableSchema schema) {

        this.schema = schema;
    }

    public TableSchema create() {
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema().setName("apm_code").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("apm_name").setType(STRING.name()),
                                new TableFieldSchema().setName("is_compliant").setType(BOOLEAN.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("cia").setType(BOOLEAN.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("lc_state").setType(STRING.name()),
                                new TableFieldSchema().setName("production_date").setType(DATE.name()),
                                new TableFieldSchema().setName("retirement_date").setType(DATE.name()),
                                new TableFieldSchema().setName("dbr_rating").setType(STRING.name()),
                                new TableFieldSchema().setName("application_tested").setType(BOOLEAN.name()),
                                new TableFieldSchema().setName("application_contact").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("manager").setType(STRING.name()),
                                new TableFieldSchema().setName("vp").setType(STRING.name()),
                                new TableFieldSchema().setName("svp").setType(STRING.name()),
                                new TableFieldSchema().setName("portfolio_owner").setType(STRING.name()),
                                new TableFieldSchema().setName("iso").setType(STRING.name())
                        )
                );
    }
}