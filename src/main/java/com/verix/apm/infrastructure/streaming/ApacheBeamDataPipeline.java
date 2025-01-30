package com.verix.apm.infrastructure.streaming;

import com.verix.apm.application.service.WriterService;
import com.verix.apm.domain.model.DataPipeline;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.apm.infrastructure.streaming.transformation.StringToApmTransformation;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final Pipeline pipeline; // construye y ejecuta las transformaciones
    private final WriterService writerService; //servicio responsable de escribir los datos
    private final RemoveLineBreaksTransformation removeLineBreaksTransformation; //remueve saltos de linea
    private final StringToApmTransformation stringToApmTransformation; //convierte String a objeto
    private final JobOptions options;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  WriterService writerService,
                                  RemoveLineBreaksTransformation removeLineBreaksTransformation,
                                  StringToApmTransformation stringToApmTransformation) {
        this.options = options;
        this.pipeline = pipeline;
        this.writerService = writerService;
        this.removeLineBreaksTransformation = removeLineBreaksTransformation;
        this.stringToApmTransformation = stringToApmTransformation;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Apm> apmList = cleanedLines.apply("Transform: Format from String to APM Class and fields", ParDo.of(stringToApmTransformation));

            apmList.apply("Load: Write APM into BigQuery", ParDo.of(writerService));

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
