package com.verix.apm;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipelineApm {

    private JobOptions options;

    public PipelineApm(String[] args) {
        initializePipelineOptions(args);//extrae los valores
        executePipeline();
    }

    //Inicializa las opciones del pipeline.
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

        // Imprime las opciones configuradas
        printPipelineOptions();
    }

    private void executePipeline() {
        Pipeline pipeline = Pipeline.create(options);

        // Paso 1: Leer el archivo CSV
        PCollection<String> rawData = readCsvFile(pipeline);

        // Paso 2: Transformar a TableRow
        PCollection<TableRow> tableRows = transformToTableRows(rawData);

        // Paso 3 Escribir en BigQuery
        //writeToBigQuery(tableRows);

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

    /**
     * Transforma las líneas del CSV en objetos TableRow para BigQuery.
     */
    private PCollection<TableRow> transformToTableRows(PCollection<String> filteredData) {
        return filteredData.apply("Transform: Convert CSV lines to TableRow",
                ParDo.of(new ConvertToTableRowFn()));
    }

    private static String convertDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                return null;
            }

            // Formato de entrada: dd-MMM-yyyy (10-Dec-2011)
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            inputFormat.setLenient(false); // Formato estricto

            // Formato de salida: yyyy-MM-dd (2025-06-30)
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);

        } catch (ParseException e) {
            System.err.println("No se pudo convertir la fecha: '" + dateStr + "' a un formato válido.");
            return null;
        } catch (Exception e) {
            System.err.println("Error inesperado al convertir la fecha: '" + dateStr + "'.");
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
                new TableFieldSchema().setName("apm_code").setType("STRING").setMode("REQUIRED"),
                new TableFieldSchema().setName("apm_name").setType("STRING"),
                new TableFieldSchema().setName("is_compliant").setType("BOOLEAN").setMode("REQUIRED"),
                new TableFieldSchema().setName("cia").setType("BOOLEAN").setMode("REQUIRED"),
                new TableFieldSchema().setName("lc_state").setType("STRING"),
                new TableFieldSchema().setName("production_date").setType("DATE"),
                new TableFieldSchema().setName("retirement_date").setType("DATE"),
                new TableFieldSchema().setName("dbr_rating").setType("STRING"),
                new TableFieldSchema().setName("application_tested").setType("BOOLEAN"),
                new TableFieldSchema().setName("application_contact").setType("STRING").setMode("REQUIRED"),
                new TableFieldSchema().setName("manager").setType("STRING"),
                new TableFieldSchema().setName("vp").setType("STRING"),
                new TableFieldSchema().setName("svp").setType("STRING").setMode("REQUIRED"),
                new TableFieldSchema().setName("portfolio_owner").setType("STRING"),
                new TableFieldSchema().setName("iso").setType("STRING")
        ));
    }

    //  transformar cada línea de texto en formato CSV en un TableRow
    private static class ConvertToTableRowFn extends DoFn<String, TableRow> {
        @ProcessElement
        public void processElement(ProcessContext context) {
            String line = context.element();
            String[] columns = parseCsvLine(line);

            if (columns.length >= 15) {
                TableRow row = new TableRow()
                        .set("apm_code", columns[0])
                        .set("apm_name", columns[1])
                        .set("is_compliant", parseBoolean(columns[2]))
                        .set("cia", parseBoolean(columns[3]))
                        .set("lc_state", columns[4])
                        .set("production_date", convertDate(columns[5]))
                        .set("retirement_date", convertDate(columns[6]))
                        .set("dbr_rating", columns[7])
                        .set("application_tested", parseBoolean(columns[8]))
                        .set("application_contact", columns[9])
                        .set("manager", columns[10])
                        .set("vp", columns[11])
                        .set("svp", columns[12])
                        .set("portfolio_owner", columns[13])
                        .set("iso", columns[14]);
                context.output(row);
            } else {
                System.out.println("Row does not have enough columns: " + Arrays.toString(columns));
            }
        }
    }

    private static String[] parseCsvLine(String line) {
        List<String> columns = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"([^\"]*)\"|([^,]+)").matcher(line);


        while (matcher.find()) {
            // Extraer el valor de la columna
            String value = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);

            if (value.equals("BJRC")){
                value=value;
            }

            if (value.trim().isEmpty()) {
                columns.add(null);
            } else {
                columns.add(value.trim());
            }
        }

        return columns.toArray(new String[0]);
    }

    private static Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return "YES".equalsIgnoreCase(value); //ignora mayus o minuscula
    }

}
