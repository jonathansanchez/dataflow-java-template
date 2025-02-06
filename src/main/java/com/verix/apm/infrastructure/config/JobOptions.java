package com.verix.apm.infrastructure.config;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface JobOptions extends PipelineOptions {
    @Description("Input for the pipeline")
    String getInput();
    void setInput(String input);

    @Description("Output Table BigQuery")
    String getOutputTable();
    void setOutputTable(String outputTable);

    @Description("Temp bucket location")
    String getTempBucket();
    void setTempBucket(String tempBucket);

    //
/*    @Description("Country CL")
    String getCl();
    void setCl(String inputCl);

    @Description("Country CCA")
    String getCca();
    void setCca(String inputCCA);

    @Description("Country CO")
    String getCo();
    void setCo(String inputCO);

    @Description("Country MX")
    String getMx();
    void setMx(String inputMX);

    @Description("Country PE")
    String getPe();
    void setPe(String inputPE);

    @Description("Country UY")
    String getUy();
    void setUy(String inputUY);

    @Description("Country UYT")
    String getUyT();
    void setUyT(String inputUYT);

    @Description("Country IB")
    String getIb();
    void setIb(String inputIB);*/
}
