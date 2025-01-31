package com.verix.apm.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.apm.application.service.WriterService;
import com.verix.apm.domain.model.DataPipeline;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.streaming.transformation.ApmToTableRow;
import com.verix.apm.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.apm.infrastructure.streaming.transformation.StringToApmTransformation;
import com.verix.apm.infrastructure.repository.model.BigQueryRepository;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import javax.swing.text.TableView;
import java.time.temporal.TemporalAccessor;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final Pipeline pipeline; // construye y ejecuta las transformaciones
    private final StringToApmTransformation stringToApmTransformation; //convierte String a objeto
    private final ApmToTableRow apmToTableRow;
    private final JobOptions options;
    private final BigQueryRepository bigQueryRepository;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  StringToApmTransformation stringToApmTransformation,
                                  ApmToTableRow apmToTableRow,
                                  BigQueryRepository bigQueryRepository) {
        this.options = options;
        this.pipeline = pipeline;
        this.stringToApmTransformation = stringToApmTransformation;
        this.apmToTableRow = apmToTableRow;
        this.bigQueryRepository = bigQueryRepository;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            rawData.apply("imprime linea", ParDo.of(new Print()));

            //PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

            PCollection<Apm> apmList = rawData.apply("Transform: Format from String to APM Class and fields", ParDo.of(stringToApmTransformation));
            // pasar a tablerow
            PCollection<TableRow> tableRoe = apmList.apply("transforma a table row",ParDo.of(apmToTableRow));

            // recibe un tablerow antes de escribir
            //BigQueryRepository(tableRoe);
            tableRoe.apply("Load: Write APM into BigQuery", bigQueryRepository.writeToBigQuery());
            //apmList.apply("Load: Write APM into BigQuery", bigQueryRepository.writeToBigQuery());

            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Print extends DoFn<String, Void> {
        @ProcessElement
        public void processElement(@Element String element, OutputReceiver<Void> out) {
            System.out.println("Elemento de rawData: " + element);
        }
    }
}
