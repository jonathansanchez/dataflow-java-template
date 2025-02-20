package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.DataPipelineStrategy;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.domain.model.Remediation;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.streaming.transformation.*;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeamDataPipelineStrategy implements DataPipelineStrategy, Serializable {

    private final JobOptions options;
    private final BigQueryRepository bigQueryRepository;
    private final RemoveLineBreaksTransformation removeLineBreaksTransformation;
    private final StringToRemediationTransformation stringToRemediationTransformation;
    private final RemediationToTableRow remediationToTableRow;
    private final TableRowToPortfolio tableRowToPortfolio;

    public BeamDataPipelineStrategy(JobOptions options,
                                    BigQueryRepository bigQueryRepository,
                                    RemoveLineBreaksTransformation removeLineBreaksTransformation,
                                    StringToRemediationTransformation stringToRemediationTransformation,
                                    RemediationToTableRow remediationToTableRow,
                                    TableRowToPortfolio tableRowToPortfolio) {
        this.options = options;
        this.bigQueryRepository = bigQueryRepository;
        this.removeLineBreaksTransformation = removeLineBreaksTransformation;
        this.stringToRemediationTransformation = stringToRemediationTransformation;
        this.remediationToTableRow = remediationToTableRow;
        this.tableRowToPortfolio = tableRowToPortfolio;
    }

    @Override
    public List<Remediation> runStrategy() {
        try {
            Pipeline pipeline = Pipeline.create(options);

            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInputFile()));

            PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Remediation> remediations = cleanedLines.apply("Transform: Format from String to Remediation Class and fields", ParDo.of(stringToRemediationTransformation));

            PCollection<TableRow> tableRows = remediations.apply("Transform: Format from Remediation Class to Table Row Class", ParDo.of(remediationToTableRow));

            tableRows.apply("Load: Write Remediation Strategy into BigQuery", bigQueryRepository.writeStrategy());

            List<Remediation> remediationList = new ArrayList<>();
            remediations.apply("Transform: Put Remediation Class into List", ParDo.of(new CaptureData<>(remediationList)));

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();

            return remediationList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<Portfolio> runCurrentStateForecast(String countryCode) {
        try {
            Pipeline pipelineCurrentState = Pipeline.create(options);

            PCollection<TableRow> dataPortfolio = pipelineCurrentState.apply("Extract: Read Portfolios " + countryCode + " from BigQuery", bigQueryRepository.readPortfolio(countryCode));

            PCollection<Portfolio> portfolios = dataPortfolio.apply("Transform: Format Table Row to Portfolio Class", ParDo.of(tableRowToPortfolio));

            List<Portfolio> portfolioList = new ArrayList<>();
            portfolios.apply("Transform: Put Portfolio Class into List", ParDo.of(new CaptureData<>(portfolioList)));

            PipelineResult result = pipelineCurrentState.run();
            result.getState();
            result.waitUntilFinish();

            return portfolioList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
