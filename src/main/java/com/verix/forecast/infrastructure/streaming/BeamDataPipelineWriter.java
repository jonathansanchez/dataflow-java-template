package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.DataPipelineReader;
import com.verix.forecast.domain.model.DataPipelineWriter;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.streaming.transformation.PortfolioToTableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.List;

public class BeamDataPipelineWriter implements DataPipelineWriter, Serializable {

    private final Pipeline pipeline;
    private final BigQueryRepository bigQueryRepository;
    private final PortfolioToTableRow portfolioToTableRow;

    public BeamDataPipelineWriter(Pipeline pipeline,
                                  BigQueryRepository bigQueryRepository,
                                  PortfolioToTableRow portfolioToTableRow) {
        this.pipeline = pipeline;
        this.bigQueryRepository = bigQueryRepository;
        this.portfolioToTableRow = portfolioToTableRow;
    }

    @Override
    public void run(List<Portfolio> portfolioList) {
        try {
            PCollection<Portfolio> components = pipeline.apply(Create.of(portfolioList));

            PCollection<TableRow> tableRows = components.apply("Transform: Format from Component Class to Table Row Class", ParDo.of(portfolioToTableRow));

            tableRows.apply("Load: Write Portfolio into BigQuery", bigQueryRepository.writeToBigQuery());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
