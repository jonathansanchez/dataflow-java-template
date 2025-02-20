package com.verix.forecast.infrastructure.config;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface JobOptions extends PipelineOptions {
    @Description("Input for the pipeline")
    String getInputFile();
    void setInputFile(String input);

    @Description("Input for the pipeline")
    String getInputPortfolio();
    void setInputPortfolio(String input);

    @Description("Output for the pipeline")
    String getOutputStrategy();
    void setOutputStrategy(String output);

    @Description("Output for the pipeline")
    String getOutputForecast();
    void setOutputForecast(String output);

    @Description("Temp bucket location")
    String getTemp();
    void setTemp(String temp);
}
