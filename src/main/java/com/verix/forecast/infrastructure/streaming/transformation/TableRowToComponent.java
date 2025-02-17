package com.verix.forecast.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.Component;
import com.verix.forecast.domain.model.Eol;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Optional;

public class TableRowToComponent extends DoFn<TableRow, Component> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        TableRow row = c.element();

        String eol = Optional
                .ofNullable(row.get("eol_last_day"))
                .map(Object::toString)
                .orElse(null);

        Component component = new Component(
                row.get("country").toString(),
                row.get("apm_code").toString(),
                row.get("sw_name").toString(),
                Eol.create(eol)
        );
        c.output(component);
    }
}
