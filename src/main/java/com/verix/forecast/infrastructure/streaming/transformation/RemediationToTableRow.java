package com.verix.forecast.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.Remediation;
import org.apache.beam.sdk.transforms.DoFn;

public class RemediationToTableRow extends DoFn<Remediation, TableRow> {

    @ProcessElement
    public void processElement(ProcessContext context) {
        Remediation remediation = context.element();
        TableRow row = new TableRow()
                .set("strategy", remediation.getStrategy())
                .set("country_code", remediation.getCountryCode().getValue())
                .set("apm_code", remediation.getApmCode())
                .set("component", remediation.getComponent())
                .set("version", remediation.getVersion())
                .set("action", remediation.getAction().getValue())
                .set("new_version", remediation.getNewVersion())
                .set("delivery_date", remediation.getDeliveryDate().getValue());
        context.output(row);
    }
}
