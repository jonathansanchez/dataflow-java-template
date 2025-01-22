package com.verix.sam.infrastructure.repository;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.sam.domain.model.Sam;
import com.verix.sam.domain.model.WriterRepository;
import com.verix.sam.infrastructure.config.JobOptions;
import com.verix.sam.infrastructure.repository.model.SamTableSchema;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.SerializableFunction;

import java.util.List;

public final class BigQueryWriterRepository implements WriterRepository {

    private final JobOptions options;
    private final SamTableSchema schema;

    public BigQueryWriterRepository(JobOptions options, SamTableSchema schema) {
        this.options = options;
        this.schema = schema;
    }

    @Override
    public void save(List<Sam> samList) {
        BigQueryIO.<Sam>write()
                .to(options.getOutputTable())
                .withFormatFunction(getSamTableRowSerializableFunction())
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withSchema(schema.create())
                .withMethod(BigQueryIO.Write.Method.STORAGE_WRITE_API);
    }

    private SerializableFunction<Sam, TableRow> getSamTableRowSerializableFunction() {
        return (Sam sam) -> new TableRow()
                .set("publisher", sam.getPublisher())
                .set("category", sam.getCategory())
                .set("product", sam.getProduct())
                .set("product_version", sam.getProductVersion())
                .set("version", sam.getVersion())
                .set("full_version", sam.getFullVersion())
                .set("edition", sam.getEdition())
                .set("internal_availability", sam.getInternalAvailability())
                .set("internal_end_of_support", sam.getInternalEOS())
                .set("publisher_availability", sam.getPublisherAvailability())
                .set("publisher_end_of_support", sam.getEos())
                .set("publisher_end_of_extended_support", sam.getEoes())
                .set("publisher_end_of_life", sam.getEol())
                .set("source", sam.getEol());
    }
}
