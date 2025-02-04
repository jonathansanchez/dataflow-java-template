package com.verix.forecast.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.model.RemediationTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

public class BigQueryRepository {
    private final JobOptions options;
    private final RemediationTableSchema remediationTableSchema;

    public BigQueryRepository(JobOptions options, RemediationTableSchema remediationTableSchema) {
        this.options  = options;
        this.remediationTableSchema = remediationTableSchema;
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeToBigQuery() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutput())
                .withSchema(remediationTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempLocation()))
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED);
    }
}
