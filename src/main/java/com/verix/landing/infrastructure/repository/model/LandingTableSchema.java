package com.verix.landing.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableSchema;

import java.util.Arrays;

import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.DATE;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.STRING;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.INT64;
import static com.google.cloud.bigquery.storage.v1.TableFieldSchema.Type.BOOL;


public class LandingTableSchema {

    private final TableSchema schema;

    public LandingTableSchema(TableSchema schema) {
        this.schema = schema;
    }

    public TableSchema create(){
        return schema
                .setFields(
                        Arrays.asList(
                                new TableFieldSchema().setName("component_id").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("apm_code").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("app_name").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("vendor").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_type").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_name").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_id").setType(INT64.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_version").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_expire_in").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("group_head_name").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("business_lines").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("dbr_tier").setType(STRING.name()),
                                new TableFieldSchema().setName("sw_valid_plan").setType(BOOL.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("app_valid_plan").setType(BOOL.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("sw_plan_status").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("plan_number").setType(INT64.name()),
                                new TableFieldSchema().setName("plan_name").setType(STRING.name()),
                                new TableFieldSchema().setName("plan_start_date").setType(DATE.name()),
                                new TableFieldSchema().setName("plan_finish_date").setType(DATE.name()),
                                new TableFieldSchema().setName("plan_funded").setType(BOOL.name()),
                                new TableFieldSchema().setName("ref_number").setType(STRING.name()),
                                new TableFieldSchema().setName("plan_comments").setType(STRING.name()),
                                new TableFieldSchema().setName("plan_external_cost").setType(INT64.name()),
                                new TableFieldSchema().setName("plan_internal_cost").setType(INT64.name()),
                                new TableFieldSchema().setName("plan_license_cost").setType(INT64.name()),
                                new TableFieldSchema().setName("eos_date").setType(DATE.name()),
                                new TableFieldSchema().setName("extended_date").setType(DATE.name()),
                                new TableFieldSchema().setName("extended_custom_date").setType(DATE.name()),
                                new TableFieldSchema().setName("local_rcmp").setType(BOOL.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("country_name").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("internet_facing").setType(BOOL.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("us_flag").setType(BOOL.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("lifecycle").setType(STRING.name()).setMode("REQUIRED"),
                                new TableFieldSchema().setName("environments").setType(STRING.name()).setMode("REQUIRED")));
    }
}
