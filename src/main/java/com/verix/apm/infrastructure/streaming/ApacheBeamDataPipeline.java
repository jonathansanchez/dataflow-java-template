package com.verix.apm.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.apm.domain.model.DataPipeline;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.streaming.transformation.ApmToTableRow;
import com.verix.apm.infrastructure.streaming.transformation.ReplaceCommasInQuotesFn;
import com.verix.apm.infrastructure.streaming.transformation.StringToApmTransformation;
import com.verix.apm.infrastructure.repository.model.BigQueryRepository;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import java.io.Serializable;

public class ApacheBeamDataPipeline implements DataPipeline {

    private final Pipeline pipeline;
    private final StringToApmTransformation stringToApmTransformation;
    private final ApmToTableRow apmToTableRow;
    private final JobOptions options;
    private final BigQueryRepository bigQueryRepository;
    private final ReplaceCommasInQuotesFn replaceCommasInQuotesFn;
    //private final AddCountryToApmTransformation addCountryToApmTransformation;

    public ApacheBeamDataPipeline(JobOptions options,
                                  Pipeline pipeline,
                                  ReplaceCommasInQuotesFn replaceCommasInQuotesFn,
                                  StringToApmTransformation stringToApmTransformation,
                                  ApmToTableRow apmToTableRow,
                                  BigQueryRepository bigQueryRepository) {
        this.options = options;
        this.pipeline = pipeline;
        this.replaceCommasInQuotesFn = replaceCommasInQuotesFn;
        this.stringToApmTransformation = stringToApmTransformation;
        this.apmToTableRow = apmToTableRow;
        this.bigQueryRepository = bigQueryRepository;
        //this.addCountryToApmTransformation = addCountryToApmTransformation;
    }

    @Override
    public void run() {
        try {
            PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

            // Filtra las filas vacias
            PCollection<String> nonEmptyLines = rawData.apply("Transform: filter empty rows", ParDo.of(new FilterEmptyRowsFn()));


            // Reemplaza por + las dobles comillas
            PCollection<String> processedData = nonEmptyLines.apply("Transform: Replace commas inside quotes", ParDo.of(replaceCommasInQuotesFn));

            //processedData.apply("imprime csv",ParDo.of(new Print()));
            //processedData.apply("imprime cnt columnas",ParDo.of(new PrintNumberOfColumns()));

            // Convierte de String a objeto
            PCollection<Apm> apmList = processedData.apply("Transform: Format from String to APM Class and fields", ParDo.of(stringToApmTransformation));
            //apmList.apply("imrime",ParDo.of(new PrintObject()));

            // Agrega el pais y devuelve un objeto
            //PCollection<Apm> apmWithCountry = apmList.apply("Add Country Column", ParDo.of(addCountryToApmTransformation));
            //apmWithCountry.apply("imrime",ParDo.of(new PrintObject()));


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

    private static class Print extends DoFn<String, Void> {
        @ProcessElement
        public void processElement(@Element String element, OutputReceiver<Void> out) {
            System.out.println("Elemento de rawData: " + element);
        }
    }


    private static class PrintNumberOfColumns extends DoFn<String, Void> {
        @ProcessElement
        public void processElement(@Element String element, OutputReceiver<Void> out) {
            String[] columns = element.split(",");

            //if (columns.length > 0) {
            if (columns.length !=15) {
                System.out.println("Índice [0]: " + columns[0] + " | Columnas: " + columns.length);
            }
        }
    }


    // DoFn para filtrar filas vacías
    private static class FilterEmptyRowsFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(@Element String line, OutputReceiver<String> out) {
            // Filtra las filas vacías (que solo contienen comas o están completamente vacías)
            if (!line.trim().isEmpty() && !line.matches("^,*$")) {
                out.output(line);
            }
        }
    }
}
