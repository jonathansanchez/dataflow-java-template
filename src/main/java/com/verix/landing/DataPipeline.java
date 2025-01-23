package com.verix.landing;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.verix.landing.domain.model.Landing;
import com.verix.landing.infrastructure.config.JobOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.coders.SerializableCoder;
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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DataPipeline {

    JobOptions options;

    public DataPipeline(String[] args) {

        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        options.setInput("gs://bucket-swc-test/landing/data/minilanding.csv");
        options.setOutput("../Datos/modified_minilandingGCS");
        options.setOutputTable("dataset_swc_test.landing");
        options.setTempBucket("gs://bucket-swc-test/temp-files/landing");

        Pipeline pipeline = Pipeline.create(options);


        PCollection<String> extractedData = pipeline.apply("Extract: Get Data from Data Lake", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

        //PCollection<String> transformFile = extractedData.apply("Transform: Order data", ParDo.of(new ReverseColumnsFn()));

        //writeBigQuery(extractedData);

        //PCollection<Landing> convertLanding = extractedData.apply("Convertir en clase Landing", getVia());
        PCollection<Landing> convertLanding = extractedData.apply("Convertir Landing", ParDo.of(new ConvertLanding()));

        convertLanding.apply("Print Landing", ParDo.of(new LandingMapper()));

        PipelineResult result = pipeline.run();
        try {
            result.getState();
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static MapElements<String, Landing> getVia() {
        return MapElements.into(TypeDescriptor.of(Landing.class))
                .via(line -> {
                    String[] splitValue = line.toString().split(",");
                    return new Landing(splitValue[0], splitValue[1], splitValue[2], splitValue[3], splitValue[4], splitValue[5]);
                });
    }

    private void writeBigQuery(PCollection<String> lines){
        String name = "Transform: Convert data in Row";

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
                        .via(line -> {
                            String[] splitCol = line.toString().split(",");
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
                                    .set("unique_component_id", row.getString("unique_component_id"))
                                    .set("apm_code", row.getString("apm_code"))
                                    .set("app_name", row.getString("app_name"))
                                    .set("vendor", row.getString("vendor"))
                                    .set("software_type", row.getString("software_type"))
                                    .set("software_name", row.getString("software_name"));
                        }))
                .apply("Load: Write data to BigQuery", BigQueryIO.writeTableRows()
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
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE));
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

    static class ConvertLanding extends DoFn<String, Landing>{
        @ProcessElement
        public void processElement(@Element String line, OutputReceiver<Landing> out) {
            String[] splitValue = line.toString().split(",");
            out.output(new Landing(splitValue[0], splitValue[1], splitValue[2], splitValue[3], splitValue[4], splitValue[5]));
        }
    }

    static class LandingMapper extends DoFn<Landing, Landing> {
        @ProcessElement
        public void processElement(@Element Landing l, OutputReceiver<Landing> out) {
            System.out.println("Landing Object: " + l.getUniqueComponentId() + ", " + l.getApmCode() + ", " + l.getAppName() + ", " + l.getVendor() + ", " + l.getSoftwareType() + ", " + l.getSoftwareName());
            out.output(l);
        }

    }
}
