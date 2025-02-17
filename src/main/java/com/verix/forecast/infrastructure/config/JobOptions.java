package com.verix.forecast.infrastructure.config;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface JobOptions extends PipelineOptions {
    @Description("Country for the pipeline")
    String getCountry();
    void setCountry(String country);

    @Description("Input for the pipeline")
    String getInput();
    void setInput(String input);

    @Description("Output for the pipeline")
    String getOutput();
    void setOutput(String output);

    @Description("Temp bucket location")
    String getTemp();
    void setTemp(String temp);
}
