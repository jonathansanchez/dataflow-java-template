package com.verix.sam.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.sam.domain.model.Sam;
import org.apache.beam.sdk.transforms.DoFn;

public class SamToTableRow extends DoFn<Sam, TableRow> {

    @ProcessElement
    public void processElement(ProcessContext context) {
        Sam sam = context.element();
        TableRow row = new TableRow()
                .set("publisher", sam.getPublisher())
                .set("category", sam.getCategory())
                .set("product", sam.getProduct())
                .set("product_version", sam.getProductVersion())
                .set("version", sam.getVersion())
                .set("full_version", sam.getFullVersion())
                .set("edition", sam.getEdition())
                .set("internal_availability", sam.getInternalAvailability().getValue())
                .set("internal_end_of_support", sam.getInternalEOS().getValue())
                .set("publisher_availability", sam.getPublisherAvailability().getValue())
                .set("publisher_end_of_support", sam.getEos().getValue())
                .set("publisher_end_of_extended_support", sam.getEoes().getValue())
                .set("publisher_end_of_life", sam.getEol().getValue())
                .set("source", sam.getSource());
        context.output(row);
    }
}
