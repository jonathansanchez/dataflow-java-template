package com.verix.apm.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.domain.model.WriterRepository;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.repository.model.ApmTableSchema;

import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.SerializableFunction;

public final class BigQueryWriterRepository implements WriterRepository {

    private final JobOptions options;
    private final ApmTableSchema schema;

    public BigQueryWriterRepository(JobOptions options, ApmTableSchema schema) {
        this.options = options;
        this.schema = schema;
    }

    // Escribe los datos en BigQuery
    @Override
    public void save(Apm apm) {
        BigQueryIO.<Apm>write()
                .to(options.getOutputTable())
                .withFormatFunction(getApmTableRowSerializableFunction())
                .withSchema(schema.create())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempBucket()))
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                .withMethod(BigQueryIO.Write.Method.STORAGE_WRITE_API);;
    }

    // Transforma el objeto Apm en TableRow
/*    private SerializableFunction<Apm, TableRow> getApmTableRowSerializableFunction() {
        return (Apm apm) -> new TableRow()
                .set("apm_code", apm.getApmCode())
                .set("apm_name", apm.getApmName())
                .set("is_compliant", apm.getIsCompliant())
                .set("cia", apm.getCia())
                .set("lc_state", apm.getLcState())
                .set("production_date", apm.getProductionDate())
                .set("retirement_date", apm.getRetirementDate())
                .set("dbr_rating", apm.getDbrRating())
                .set("application_tested", apm.getApplicationTested())
                .set("application_contact", apm.getApplicationContact())
                .set("manager", apm.getManager())
                .set("vp", apm.getVp())
                .set("svp", apm.getSvp())
                .set("portfolio_owner", apm.getPortfolioOwner())
                .set("iso", apm.getIso());
    }*/

    private SerializableFunction<Apm, TableRow> getApmTableRowSerializableFunction() {
        return (Apm apm) -> {
            try {
                // Comprueba si todos los campos requeridos del objeto 'Apm' están presentes.
                if (apm == null ||
                        apm.getApmCode() == null || apm.getApmName() == null || apm.getIsCompliant() == null ||
                        apm.getCia() == null || apm.getLcState() == null || apm.getProductionDate() == null ||
                        apm.getRetirementDate() == null || apm.getDbrRating() == null || apm.getApplicationTested() == null ||
                        apm.getApplicationContact() == null || apm.getManager() == null || apm.getVp() == null ||
                        apm.getSvp() == null || apm.getPortfolioOwner() == null || apm.getIso() == null) {

                    // Si falta algún campo lo imprime
                    System.out.println("Error: Apm object has missing fields or is invalid: " + apm);
                    return null;
                }

                // Si todos los datos son válidos, convertimos a TableRow
                return new TableRow()
                        .set("apm_code", apm.getApmCode())
                        .set("apm_name", apm.getApmName())
                        .set("is_compliant", apm.getIsCompliant())
                        .set("cia", apm.getCia())
                        .set("lc_state", apm.getLcState())
                        .set("production_date", apm.getProductionDate())
                        .set("retirement_date", apm.getRetirementDate())
                        .set("dbr_rating", apm.getDbrRating())
                        .set("application_tested", apm.getApplicationTested())
                        .set("application_contact", apm.getApplicationContact())
                        .set("manager", apm.getManager())
                        .set("vp", apm.getVp())
                        .set("svp", apm.getSvp())
                        .set("portfolio_owner", apm.getPortfolioOwner())
                        .set("iso", apm.getIso());

            } catch (Exception e) {
                System.out.println("Error converting Apm to TableRow: " + apm + " Exception: " + e.getMessage());
                return null;
            }
        };
    }

}
