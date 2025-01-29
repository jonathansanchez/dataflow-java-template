package com.verix.apm;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface JobOptions extends PipelineOptions {
    @Description("Input for the pipeline")
    String getInput();
    void setInput(String input);

    @Description("Output for the pipeline")
    String getOutput();
    void setOutput(String output);

    @Description("Output Table BigQuery")
    String getOutputTable();
    void setOutputTable(String outputTable);

    @Description("Temp bucket location")
    String getTempBucket();
    void setTempBucket(String tempBucket);
}
