package com.verix.sam.infrastructure.streaming;

import com.verix.sam.application.service.WriterService;
import com.verix.sam.domain.model.DataPipeline;
import com.verix.sam.domain.model.Sam;
import com.verix.sam.infrastructure.config.JobOptions;
import com.verix.sam.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.sam.infrastructure.streaming.transformation.StringToSamTransformation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final Pipeline pipeline;
    private final WriterService writerService;
    private final RemoveLineBreaksTransformation removeLineBreaksTransformation;
    private final StringToSamTransformation stringToSamTransformation;
    private final JobOptions options;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  WriterService writerService,
                                  RemoveLineBreaksTransformation removeLineBreaksTransformation,
                                  StringToSamTransformation stringToSamTransformation) {
        this.options = options;
        this.pipeline = pipeline;
        this.writerService = writerService;
        this.removeLineBreaksTransformation = removeLineBreaksTransformation;
        this.stringToSamTransformation = stringToSamTransformation;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Sam> samList = cleanedLines.apply("Transform: Format from String to SAM Class and fields", ParDo.of(stringToSamTransformation));

            samList.apply("Load: Write SAM into BigQuery", ParDo.of(writerService));

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
