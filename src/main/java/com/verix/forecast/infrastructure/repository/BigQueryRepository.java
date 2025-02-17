package com.verix.forecast.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.model.PortfolioTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

import java.io.Serializable;

public class BigQueryRepository implements Serializable {
    private final JobOptions options;
    private final PortfolioTableSchema portfolioTableSchema;

    public BigQueryRepository(JobOptions options, PortfolioTableSchema portfolioTableSchema) {
        this.options  = options;
        this.portfolioTableSchema = portfolioTableSchema;
    }

    public BigQueryIO.TypedRead<@UnknownKeyFor @NonNull @Initialized TableRow> readLanding() {
        String query = String.format("SELECT country, apm_code, sw_name, eol_last_day FROM `%s` WHERE country = '%s'",
                options.getInput(),
                options.getCountry());
        return BigQueryIO.readTableRows().fromQuery(query).usingStandardSql();
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeToBigQuery() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutput())
                .withSchema(portfolioTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempLocation()))
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED);
    }
}
