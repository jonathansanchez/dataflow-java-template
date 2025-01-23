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
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class PipelineApm {

    private JobOptions options;

    public PipelineApm(String[] args) {
        initializePipelineOptions(args);
        executePipeline();
    }

    private void initializePipelineOptions(String[] args) {
        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        options.setInput(System.getenv("INPUT_PATH"));
        options.setOutput(System.getenv("OUTPUT_PATH"));
        options.setOutputTable(System.getenv("GCP_TABLE"));
        options.setTempBucket(System.getenv("TEMP_LOCATION"));

        printPipelineOptions();
    }

    private void executePipeline() {
        Pipeline pipeline = Pipeline.create(options);

        // Paso 1: Leer el archivo CSV
        PCollection<String> rawData = readCsvFile(pipeline);

        // Paso 2: Transformar a TableRow
        PCollection<TableRow> tableRows = transformToTableRows(rawData);

        // Paso 3: Escribir en BigQuery
        writeToBigQuery(tableRows);

        // Paso 4: Ejecutar el pipeline
        runPipeline(pipeline);
    }

    private void printPipelineOptions() {
        System.out.println("Input: " + options.getInput());
        System.out.println("Output: " + options.getOutput());
        System.out.println("OutputTable: " + options.getOutputTable());
        System.out.println("TempBucket: " + options.getTempBucket());
    }

    private PCollection<String> readCsvFile(Pipeline pipeline) {
        return pipeline.apply("Extract:Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));
    }

    private PCollection<TableRow> transformToTableRows(PCollection<String> filteredData) {
        return filteredData.apply("Transform: Transforms the CSV lines into TableRow objects", MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via(line -> {
                    String[] columns = line.split(","); // Divide cada línea del CSV por las comas

                    // Convierte las fechas
                    String productionDate = convertDate(columns[5]);
                    String retirementDate = convertDate(columns[6]);

                    boolean isCompliant = "YES".equalsIgnoreCase(columns[2]);
                    boolean cia = "YES".equalsIgnoreCase(columns[3]);
                    boolean applicationTested = "YES".equalsIgnoreCase(columns[8]);

                    return new TableRow()
                            .set("apm_code", columns[0])
                            .set("apm_name", columns[1])
                            .set("is_compliant", isCompliant)
                            .set("cia", cia)
                            .set("lc_state", columns[4])
                            .set("production_date", productionDate)
                            .set("retirement_date", retirementDate)
                            .set("dbr_rating", columns[7])
                            .set("application_tested", applicationTested)
                            .set("application_contact", columns[9])
                            .set("manager", columns[10])
                            .set("vp", columns[11])
                            .set("svp", columns[12])
                            .set("portfolio_owner", columns[13])
                            .set("iso", columns[14]);
                }));
    }

    private static String convertDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd-MM-yy");
            inputFormat1.setLenient(false); //requiere un formato valido de fecha

            Calendar calendar = Calendar.getInstance();
            calendar.set(2000, Calendar.JANUARY, 1); // Rango de años: 2000-2099
            inputFormat1.set2DigitYearStart(calendar.getTime());

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = null;
            try {
                date = inputFormat1.parse(dateStr);
            } catch (ParseException e) {
                SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd-MMM-yy");
                inputFormat2.setLenient(false);
                try {
                    date = inputFormat2.parse(dateStr);
                } catch (ParseException ex) {
                    System.err.println("Error al convertir la fecha: " + dateStr);
                    return null;
                }
            }

            return outputFormat.format(date);

        } catch (Exception e) {
            System.err.println("Error al convertir la fecha: " + dateStr);
            return null;
        }
    }


    private void writeToBigQuery(PCollection<TableRow> tableRows) {
        tableRows.apply("Write to BigQuery", BigQueryIO.writeTableRows()
                .to(options.getOutputTable())
                .withSchema(getBigQuerySchema())
                .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(options.getTempBucket()))
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE));
    }

    private void runPipeline(Pipeline pipeline) {
        PipelineResult result = pipeline.run();
        try {
            result.waitUntilFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TableSchema getBigQuerySchema() {
        return new TableSchema().setFields(Arrays.asList(
                new TableFieldSchema().setName("apm_code").setType("STRING"),
                new TableFieldSchema().setName("apm_name").setType("STRING"),
                new TableFieldSchema().setName("is_compliant").setType("BOOLEAN"),
                new TableFieldSchema().setName("cia").setType("BOOLEAN"),
                new TableFieldSchema().setName("lc_state").setType("STRING"),
                new TableFieldSchema().setName("production_date").setType("DATE"),
                new TableFieldSchema().setName("retirement_date").setType("DATE"),
                new TableFieldSchema().setName("dbr_rating").setType("STRING"),
                new TableFieldSchema().setName("application_tested").setType("BOOLEAN"),
                new TableFieldSchema().setName("application_contact").setType("STRING"),
                new TableFieldSchema().setName("manager").setType("STRING"),
                new TableFieldSchema().setName("vp").setType("STRING"),
                new TableFieldSchema().setName("svp").setType("STRING"),
                new TableFieldSchema().setName("portfolio_owner").setType("STRING"),
                new TableFieldSchema().setName("iso").setType("STRING")
        ));
    }
}
