package com.verix.sam.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.sam.infrastructure.config.JobOptions;
import com.verix.sam.infrastructure.repository.model.SamTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

public class BigQueryRepository {
    private final JobOptions options;
    private final SamTableSchema samTableSchema;

    public BigQueryRepository(JobOptions options, SamTableSchema samTableSchema) {
        this.options        = options;
        this.samTableSchema = samTableSchema;
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeToBigQuery() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutput())
                .withSchema(samTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempLocation()))
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                //.withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED);
    }
}
