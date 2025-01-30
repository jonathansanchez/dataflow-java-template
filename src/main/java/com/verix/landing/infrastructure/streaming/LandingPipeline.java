package com.verix.landing.infrastructure.streaming;

import com.verix.landing.DeletePipeline;
import com.verix.landing.domain.model.DataPipeline;
import com.verix.landing.domain.model.Landing;
import com.verix.landing.infrastructure.config.JobOptions;
import com.verix.landing.infrastructure.streaming.transformations.ConvertToLanding;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class LandingPipeline implements DataPipeline {

    private final JobOptions options;
    private final Pipeline pipeline;

    public LandingPipeline(JobOptions options, Pipeline pipeline) {
        this.options = options;
        this.pipeline = pipeline;
    }

    @Override
    public void run() {
        try{
            // Extract
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV from Data Lake", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));
            // Transform
            PCollection<Landing> landingData = rawData.apply("Transform: Conversion to Landing Object", ParDo.of(new ConvertToLanding()));
            // Load
            landingData.apply("Load: Write Landing to BigQuery", ParDo.of(new ConvertToLanding()));

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
