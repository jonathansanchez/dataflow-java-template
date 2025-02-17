package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.forecast.application.service.ForecastService;
import com.verix.forecast.domain.model.Component;
import com.verix.forecast.domain.model.DataPipelineReader;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.streaming.transformation.ComponentToListTransformation;
import com.verix.forecast.infrastructure.streaming.transformation.TableRowToComponent;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeamDataPipelineReader implements DataPipelineReader, Serializable {

    private final Pipeline pipeline;
    private final BigQueryRepository bigQueryRepository;
    private final TableRowToComponent tableRowToComponent;
    private final ForecastService forecastService;

    public BeamDataPipelineReader(Pipeline pipeline,
                                  BigQueryRepository bigQueryRepository,
                                  TableRowToComponent tableRowToComponent,
                                  ForecastService forecastService) {
        this.pipeline = pipeline;
        this.bigQueryRepository = bigQueryRepository;
        this.tableRowToComponent = tableRowToComponent;
        this.forecastService = forecastService;
    }

    @Override
    public void run() {
        try {
            PCollection<TableRow> data = pipeline.apply("Extract: Read from BigQuery", bigQueryRepository.readLanding());

            PCollection<Component> components = data.apply("Transform: Format from String to Component Class", ParDo.of(tableRowToComponent));

            List<Component> componentList = new ArrayList<>();

            components.apply("Transform: Component Class into List", ParDo.of(new ComponentToListTransformation<>(componentList)));


            pipeline.run().waitUntilFinish();
            forecastService.execute(componentList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
