package com.verix.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class DataPipeline {
    public static void main(String[] args) {

        PipelineOptionsFactory.register(JobOptions.class);
        JobOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        options.setInput("gs://bucket-swc-test/Sources/Landing/20250110/minilanding.csv");
        options.setOutput("../Datos/modified_minilandingGCS");
        options.setOutputTable("dataset_swc_test.landing");
        options.setTempBucket("gs://bucket-swc-test/temp-files/landing");

        Pipeline pipeline = Pipeline.create(options);


        PCollection<String> extractedData = pipeline.apply("Read Data GCS", TextIO.read().from(options.getInput()));

        PCollection<String> transformFile = extractedData.apply("Reverse Columns", ParDo.of(new ReverseColumnsFn()));

        PipelineResult result = pipeline.run();
        try {
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ReverseColumnsFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(@Element String line, OutputReceiver<String> out) {
            String[] columns = line.split(",");
            for (int i = 0; i < columns.length; i++) {
                columns[i] = new StringBuilder(columns[i]).reverse().toString();
            }
            out.output(String.join(",", columns));
        }
    }
}
