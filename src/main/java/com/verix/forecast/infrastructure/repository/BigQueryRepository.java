package com.verix.forecast.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.model.PortfolioTableSchema;
import com.verix.forecast.infrastructure.repository.model.RemediationTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

public class BigQueryRepository {
    private final JobOptions             options;
    private final RemediationTableSchema remediationTableSchema;
    private final PortfolioTableSchema   portfolioTableSchema;

    public BigQueryRepository(JobOptions options,
                              RemediationTableSchema remediationTableSchema,
                              PortfolioTableSchema portfolioTableSchema) {
        this.options                = options;
        this.remediationTableSchema = remediationTableSchema;
        this.portfolioTableSchema   = portfolioTableSchema;
    }

    public BigQueryIO.TypedRead<@UnknownKeyFor @NonNull @Initialized TableRow> readPortfolio(String country) {
        String query = String.format("SELECT country, country_name, portfolio_date, expired, expired_kri, expiring, expiring_kri, total " +
                        "FROM `%s` " +
                        "WHERE country = '%s' " +
                        "ORDER BY portfolio_date ASC",
                options.getInputPortfolio(),
                country);
        return BigQueryIO.readTableRows().fromQuery(query).usingStandardSql();
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeStrategy() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutputStrategy())
                .withSchema(remediationTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempLocation()))
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED);
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeForecast() {
        return BigQueryIO.writeTableRows()
                .to(options.getOutputForecast())
                .withSchema(portfolioTableSchema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempLocation()))
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED);
    }
}
