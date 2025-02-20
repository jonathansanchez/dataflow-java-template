package com.verix.forecast.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.Portfolio;
import org.apache.beam.sdk.transforms.DoFn;

import java.time.LocalDate;

public class TableRowToPortfolio  extends DoFn<TableRow, Portfolio> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        TableRow row = c.element();

        Portfolio portfolio = new Portfolio(
                row.get("country").toString(),
                LocalDate.parse(row.get("portfolio_date").toString()),
                Integer.valueOf(row.get("total").toString()),
                Integer.valueOf(row.get("expired").toString()),
                Integer.valueOf(row.get("expiring").toString())
        );
        c.output(portfolio);
    }
}
