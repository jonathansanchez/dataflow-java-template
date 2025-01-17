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
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TypeDescriptor;

import java.util.Arrays;

public class DataPipeline {
    JobOptions options;
    public DataPipeline(String[] args) {

        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory
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

        writeBigQuery(transformFile);

        PipelineResult result = pipeline.run();
        try {
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBigQuery(PCollection<String> lines){
        String name = "Write Data Big Query";

        // Declare bigquery schema
        var schema = Schema.builder()
                .addStringField("unique_component_id")
                .addStringField("apm_code")
                .addStringField("app_name")
                .addStringField("vendor")
                .addStringField("software_type")
                .addStringField("software_name")
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
                .apply("Convert to BigQuery TableRow", MapElements.into(TypeDescriptor.of(TableRow.class))
                        .via(row -> {
                            System.out.println("Row: " + row);
                            assert row != null;
                            return new TableRow()
                                    .set("unique_component_id", row.getString("unique_component_id"))
                                    .set("apm_code", row.getString("apm_code"))
                                    .set("app_name", row.getString("app_name"))
                                    .set("vendor", row.getString("vendor"))
                                    .set("software_type", row.getString("software_type"))
                                    .set("software_name", row.getString("software_name"));
                        }))
                .apply("Write to BigQuery", BigQueryIO.writeTableRows()
                        .to(options.getOutputTable())
                        .withSchema(new TableSchema().setFields(Arrays.asList(
                                new TableFieldSchema().setName("unique_component_id").setType("STRING"),
                                new TableFieldSchema().setName("apm_code").setType("STRING"),
                                new TableFieldSchema().setName("app_name").setType("STRING"),
                                new TableFieldSchema().setName("vendor").setType("STRING"),
                                new TableFieldSchema().setName("software_type").setType("STRING"),
                                new TableFieldSchema().setName("software_name").setType("STRING"))))
                        .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempBucket()))
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));
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
