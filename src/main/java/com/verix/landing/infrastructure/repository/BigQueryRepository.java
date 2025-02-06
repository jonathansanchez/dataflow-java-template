package com.verix.landing.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.landing.domain.model.Landing;
import com.verix.landing.infrastructure.config.JobOptions;
import com.verix.landing.infrastructure.repository.model.LandingTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

public class BigQueryRepository {

    private final JobOptions options;
    private final LandingTableSchema landingTableSchema;

    public BigQueryRepository(JobOptions options, LandingTableSchema landingTableSchema) {
        this.options = options;
        this.landingTableSchema = landingTableSchema;
    }

    public BigQueryIO.@UnknownKeyFor @NonNull @Initialized Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeToBigQuery() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutput())
                .withSchema(landingTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTemp()))
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE);
    }
}
