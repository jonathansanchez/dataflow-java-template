package com.verix.example;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;

import java.util.Arrays;

public class PipelineApm {

    JobOptions options;

    public PipelineApm(String[] args) {

        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        options.setInput("gs://bucket-swc-test/Sources/APM/20250114/apm.csv");
        options.setOutput("../Datos/modified_minilandingGCS");
        options.setOutputTable("dataset_swc_test.apm");
        options.setTempBucket("gs://bucket-swc-test/temp-files/APM");


        Pipeline pipeline = Pipeline.create(options);


        PCollection<String> extractedData = pipeline.apply("Extract: Get Data from Data Lake", TextIO.read().from(options.getInput()));


        PCollection<String> transformFile = processCsv(extractedData);

        writeBigQuery(transformFile);

        PipelineResult result = pipeline.run();
        try {
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PCollection<String> processCsv(PCollection<String> lines) {
        return lines.apply("Transform: skip headers", Filter.by((String line) -> !line.startsWith("AppCode")))
                .apply("Transform: Filter extra rows", Filter.by((String line) -> {
                    String[] splitLine = line.split(",");
                    return splitLine.length <= 15;  // Si el número de campos es mayor a 15, descartar la fila
                }))
                .apply("ProcessCSV", ParDo.of(new ProcessCsvFn()));
    }

    private void writeBigQuery(PCollection<String> lines){
        String name = "Transform: Convert data in Row";

        // Declare bigquery schema
        var schema = Schema.builder()
                .addStringField("AppCode")
                .addStringField("AppName")
                .addStringField("IsCompliant")
                .addStringField("CriticalAssetApp")
                .addStringField("LCState")
                .addStringField("ProdDate")
                .addStringField("ProdDate2")
                .addStringField("DBRRating")
                .addStringField("TestConducted1")
                .addStringField("MDU1")
                .addStringField("MDUMgr1")
                .addStringField("VP")
                .addStringField("SVP")
                .addStringField("PortfolioOwner")
                .addStringField("BizOwner1")
                .build();

        // Transformar lineas a columnas
        lines.apply(name, MapElements.into(TypeDescriptor.of(Row.class))
                        .via(col -> {
                            System.out.println("Columna: " + col);
                            String[] splitCol = col.toString().split(",");
                            Row.Builder row = Row.withSchema(schema);
                            for(String value : splitCol){
                                row.addValue(value);
                            }
                            return row.build();

                        }))
                .setRowSchema(schema)

                //Convertir a tipo de dato TableRow de BQ
                .apply("Transform: Convert data in TableRow", MapElements.into(TypeDescriptor.of(TableRow.class))
                        .via(row -> {
                            System.out.println("Row: " + row);
                            assert row != null;
                            return new TableRow()
                                    .set("AppCode", row.getString("AppCode"))
                                    .set("AppName", row.getString("AppName"))
                                    .set("IsCompliant", row.getString("IsCompliant"))
                                    .set("CriticalAssetApp", row.getString("CriticalAssetApp"))
                                    .set("LCState", row.getString("LCState"))
                                    .set("ProdDate", row.getString("ProdDate"))
                                    .set("ProdDate2", row.getString("ProdDate2"))
                                    .set("DBRRating", row.getString("DBRRating"))
                                    .set("TestConducted1", row.getString("TestConducted1"))
                                    .set("MDU1", row.getString("MDU1"))
                                    .set("MDUMgr1", row.getString("MDUMgr1"))
                                    .set("VP", row.getString("VP"))
                                    .set("SVP", row.getString("SVP"))
                                    .set("PortfolioOwner", row.getString("PortfolioOwner"))
                                    .set("BizOwner1", row.getString("BizOwner1"));
                        }))
                .apply("Load: Write data to BigQuery", BigQueryIO.writeTableRows()
                        .to(options.getOutputTable())
                        .withSchema(new TableSchema().setFields(Arrays.asList(
                                new TableFieldSchema().setName("AppCode").setType("STRING"),
                                new TableFieldSchema().setName("AppName").setType("STRING"),
                                new TableFieldSchema().setName("IsCompliant").setType("STRING"),
                                new TableFieldSchema().setName("CriticalAssetApp").setType("STRING"),
                                new TableFieldSchema().setName("LCState").setType("STRING"),
                                new TableFieldSchema().setName("ProdDate").setType("STRING"),
                                new TableFieldSchema().setName("ProdDate2").setType("STRING"),
                                new TableFieldSchema().setName("DBRRating").setType("STRING"),
                                new TableFieldSchema().setName("TestConducted1").setType("STRING"),
                                new TableFieldSchema().setName("MDU1").setType("STRING"),
                                new TableFieldSchema().setName("MDUMgr1").setType("STRING"),
                                new TableFieldSchema().setName("VP").setType("STRING"),
                                new TableFieldSchema().setName("SVP").setType("STRING"),
                                new TableFieldSchema().setName("PortfolioOwner").setType("STRING"),
                                new TableFieldSchema().setName("BizOwner1").setType("STRING"))))
                        .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempBucket()))
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)); // WRITE_APPEND/WRITE_TRUNCATE
    }

    static class ProcessCsvFn extends DoFn<String, String>{
        @ProcessElement
        public void processElement(@Element String line, OutputReceiver<String> out){
            //System.out.println("Processed Line: " + line);
            out.output(line);
        }
    }
}
