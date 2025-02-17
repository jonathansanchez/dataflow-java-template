package com.verix.forecast.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.Portfolio;
import org.apache.beam.sdk.transforms.DoFn;

public class PortfolioToTableRow extends DoFn<Portfolio, TableRow> {

    @ProcessElement
    public void processElement(ProcessContext context) {
        Portfolio portfolio = context.element();
        TableRow row = new TableRow()
                .set("country", portfolio.getCountry())
                .set("portfolio_date", portfolio.getDate())
                .set("expired", portfolio.getExpired())
                .set("expired_kri", portfolio.getExpiredKri())
                .set("expiring", portfolio.getExpiring())
                .set("expiring_kri", portfolio.getExpiringKri())
                .set("total", portfolio.getTotal())
                .set("created_at", portfolio.getCreatedAt());
        context.output(row);
    }
}
