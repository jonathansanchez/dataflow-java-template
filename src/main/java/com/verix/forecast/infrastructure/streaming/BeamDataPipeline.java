package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.domain.model.DataPipeline;
import com.verix.forecast.domain.model.Remediation;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.streaming.transformation.RemediationToTableRow;
import com.verix.forecast.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.forecast.infrastructure.streaming.transformation.StringToRemediationTransformation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class BeamDataPipeline implements DataPipeline {

    private final JobOptions options;
    private final Pipeline pipeline;
    private final BigQueryRepository bigQueryRepository;
    private final RemoveLineBreaksTransformation removeLineBreaksTransformation;
    private final StringToRemediationTransformation stringToRemediationTransformation;
    private final RemediationToTableRow remediationToTableRow;

    public BeamDataPipeline(JobOptions options,
                            Pipeline pipeline,
                            BigQueryRepository bigQueryRepository,
                            RemoveLineBreaksTransformation removeLineBreaksTransformation,
                            StringToRemediationTransformation stringToRemediationTransformation,
                            RemediationToTableRow remediationToTableRow) {
        this.options = options;
        this.pipeline = pipeline;
        this.bigQueryRepository = bigQueryRepository;
        this.removeLineBreaksTransformation = removeLineBreaksTransformation;
        this.stringToRemediationTransformation = stringToRemediationTransformation;
        this.remediationToTableRow = remediationToTableRow;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Remediation> remediationList = cleanedLines.apply("Transform: Format from String to SAM Class and fields", ParDo.of(stringToRemediationTransformation));

            PCollection<TableRow> tableRows = remediationList.apply("Transform: Format from SAM Class to Table Row Class", ParDo.of(remediationToTableRow));

            tableRows.apply("Load: Write SAM into BigQuery", bigQueryRepository.writeToBigQuery());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
