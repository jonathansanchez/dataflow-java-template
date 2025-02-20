package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.DataPipelineForecast;
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

public class BeamDataPipelineForecast implements DataPipelineForecast, Serializable {

    private final JobOptions options;
    private final BigQueryRepository bigQueryRepository;
    private final PortfolioToTableRow portfolioToTableRow;

    public BeamDataPipelineForecast(JobOptions options,
                                  BigQueryRepository bigQueryRepository,
                                  PortfolioToTableRow portfolioToTableRow) {
        this.options = options;
        this.bigQueryRepository = bigQueryRepository;
        this.portfolioToTableRow = portfolioToTableRow;
    }

    @Override
    public void run(List<Portfolio> portfolioList) {
        try {
            Pipeline pipeline = Pipeline.create(options);

            PCollection<Portfolio> portfolios = pipeline.apply(Create.of(portfolioList));

            PCollection<TableRow> tableRows = portfolios.apply("Transform: Format from Forecast Portfolio Class to Table Row Class", ParDo.of(portfolioToTableRow));

            tableRows.apply("Load: Write Forecast Portfolio into BigQuery", bigQueryRepository.writeForecast());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
