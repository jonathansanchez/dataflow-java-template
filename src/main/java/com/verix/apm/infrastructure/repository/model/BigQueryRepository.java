package com.verix.apm.infrastructure.repository.model;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.repository.model.ApmTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

public class BigQueryRepository {
    private final JobOptions options;
    private final ApmTableSchema apmTableSchema;

    public BigQueryRepository(JobOptions options, ApmTableSchema apmTableSchema) {
        this.options        = options;
        this.apmTableSchema = apmTableSchema;
    }

    public BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeToBigQuery() {
        try {
            BigQueryIO.Write<@UnknownKeyFor @NonNull @Initialized TableRow> writeOperation = BigQueryIO.writeTableRows()
                    .to(options.getOutputTable())
                    .withSchema(apmTableSchema.create())
                    .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempBucket()))
                    .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                    .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE); // WRITE_APPEND - WRITE_TRUNCATE
                    //.withMethod(BigQueryIO.Write.Method.STORAGE_WRITE_API);

            System.out.println("Datos insertados correctamente en BigQuery.");

            return writeOperation;
        } catch (Exception e) {
            System.err.println("Error al insertar los datos en BigQuery: " + e.getMessage());
            throw new RuntimeException("Error al insertar los datos en BigQuery", e);
        }
    }
}
