package com.verix.landing.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.landing.domain.model.DataPipeline;
import com.verix.landing.domain.model.Landing;
import com.verix.landing.infrastructure.config.JobOptions;
import com.verix.landing.infrastructure.repository.BigQueryRepository;
import com.verix.landing.infrastructure.streaming.transformations.ConvertLandingToTableRow;
import com.verix.landing.infrastructure.streaming.transformations.ConvertStringToLanding;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class LandingPipeline implements DataPipeline {

    private final JobOptions options;
    private final Pipeline pipeline;
    private final ConvertStringToLanding convertStringToLanding;
    private final ConvertLandingToTableRow convertLandingToTableRow;
    private final BigQueryRepository bigQueryRepository;

    public LandingPipeline(JobOptions options, Pipeline pipeline, ConvertStringToLanding convertStringToLanding, ConvertLandingToTableRow convertLandingToTableRow, BigQueryRepository bigQueryRepository) {
        this.options = options;
        this.pipeline = pipeline;
        this.convertStringToLanding = convertStringToLanding;
        this.convertLandingToTableRow = convertLandingToTableRow;
        this.bigQueryRepository = bigQueryRepository;
    }

    @Override
    public void run() {
        try{
            // Extract
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV from Data Lake", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));
            // Transform
            PCollection<Landing> landingData = rawData.apply("Transform: Conversion from String to Landing", ParDo.of(convertStringToLanding));
            PCollection<TableRow> tableRowData = landingData.apply("Transform: Conversion from Landing to TableRow", ParDo.of(convertLandingToTableRow));
            // Load
            tableRowData.apply("Load: Write Landing to BigQuery", bigQueryRepository.writeToBigQuery());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
