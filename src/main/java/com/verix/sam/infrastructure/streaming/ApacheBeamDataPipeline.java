package com.verix.sam.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.sam.domain.model.DataPipeline;
import com.verix.sam.domain.model.Sam;
import com.verix.sam.infrastructure.config.JobOptions;
import com.verix.sam.infrastructure.repository.BigQueryRepository;
import com.verix.sam.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.sam.infrastructure.streaming.transformation.SamToTableRow;
import com.verix.sam.infrastructure.streaming.transformation.StringToSamTransformation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final JobOptions options;
    private final Pipeline pipeline;
    private final BigQueryRepository bigQueryRepository;
    private final RemoveLineBreaksTransformation removeLineBreaksTransformation;
    private final StringToSamTransformation stringToSamTransformation;
    private final SamToTableRow samToTableRow;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  BigQueryRepository bigQueryRepository,
                                  RemoveLineBreaksTransformation removeLineBreaksTransformation,
                                  StringToSamTransformation stringToSamTransformation,
                                  SamToTableRow samToTableRow) {
        this.options = options;
        this.pipeline = pipeline;
        this.bigQueryRepository = bigQueryRepository;
        this.removeLineBreaksTransformation = removeLineBreaksTransformation;
        this.stringToSamTransformation = stringToSamTransformation;
        this.samToTableRow = samToTableRow;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Sam> samList = cleanedLines.apply("Transform: Format from String to SAM Class and fields", ParDo.of(stringToSamTransformation));

            PCollection<TableRow> tableRows = samList.apply("Transform: Format from SAM Class to Table Row Class", ParDo.of(samToTableRow));

            tableRows.apply("Load: Write SAM into BigQuery", bigQueryRepository.writeToBigQuery());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
