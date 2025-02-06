package com.verix.apm.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.gson.Gson;
import com.verix.apm.domain.model.DataPipeline;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.streaming.transformation.ApmToTableRow;
import com.verix.apm.infrastructure.streaming.transformation.ReplaceCommasInQuotesFn;
import com.verix.apm.infrastructure.streaming.transformation.StringToApmTransformation;
import com.verix.apm.infrastructure.streaming.transformation.FilterEmptyRowsFn;
import com.verix.apm.infrastructure.repository.model.BigQueryRepository;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final Pipeline pipeline;
    private final StringToApmTransformation stringToApmTransformation;
    private final FilterEmptyRowsFn filterEmptyRowsFn;
    private final ApmToTableRow apmToTableRow;
    private final JobOptions options;
    private final BigQueryRepository bigQueryRepository;
    private final ReplaceCommasInQuotesFn replaceCommasInQuotesFn;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  FilterEmptyRowsFn filterEmptyRowsFn,
                                  ReplaceCommasInQuotesFn replaceCommasInQuotesFn,
                                  StringToApmTransformation stringToApmTransformation,
                                  ApmToTableRow apmToTableRow,
                                  BigQueryRepository bigQueryRepository) {
        this.options = options;
        this.pipeline = pipeline;
        this.filterEmptyRowsFn = filterEmptyRowsFn;
        this.replaceCommasInQuotesFn = replaceCommasInQuotesFn;
        this.stringToApmTransformation = stringToApmTransformation;
        this.apmToTableRow = apmToTableRow;
        this.bigQueryRepository = bigQueryRepository;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            PCollection<String> nonEmptyLines = rawData.apply("Filter: Remove empty rows", ParDo.of(filterEmptyRowsFn));

            PCollection<String> processedData = nonEmptyLines.apply("Transform: Replace commas inside quotes", ParDo.of(replaceCommasInQuotesFn));
            //processedData.apply("imprime csv",ParDo.of(new Print()));
            //processedData.apply("imprime cnt columnas",ParDo.of(new PrintNumberOfColumns()));

            // Convierte de String a objeto
            PCollection<Apm> apmList = processedData.apply("Transform: Format from String to APM Class and fields", ParDo.of(stringToApmTransformation));
            //apmList.apply("Imprimir objetos Apm", ParDo.of(new PrintObject()));


            // Convertir de objeto a tablerow
            PCollection<TableRow> tableRows  = apmList.apply("Transform: Format from APM Class to Table Row Class",ParDo.of(apmToTableRow));


            tableRows.apply("Load: Write APM into BigQuery", bigQueryRepository.writeToBigQuery());


            PipelineResult result = pipeline.run();
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private static class Print extends DoFn<String, Void> {
        @ProcessElement
        public void processElement(@Element String element, OutputReceiver<Void> out) {
            System.out.println("Elemento de rawData: " + element);
        }
    }*/


/*    private static class PrintNumberOfColumns extends DoFn<String, Void> {
        @ProcessElement
        public void processElement(@Element String element, OutputReceiver<Void> out) {
            String[] columns = element.split(",");

            //if (columns.length > 0) {
            if (columns.length !=15) {
                System.out.println("Índice [0]: " + columns[0] + " | Columnas: " + columns.length);
            }
        }
    }*/

/*    private static class PrintObject extends DoFn<Apm, Void> {
        private static final Gson gson = new Gson();

        @ProcessElement
        public void processElement(@Element Apm apm, OutputReceiver<Void> out) {
            System.out.println("Objeto Apm: " + gson.toJson(apm));
        }
    }*/

}
