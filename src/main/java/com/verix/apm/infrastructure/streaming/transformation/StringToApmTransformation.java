package com.verix.apm.infrastructure.streaming.transformation;

import com.verix.apm.domain.model.LifeDate;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StringToApmTransformation extends DoFn<String, Apm> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, ProcessContext context,  OutputReceiver<Apm> out) {
        JobOptions options = context.getPipelineOptions().as(JobOptions.class);

        List<String> splitValue = Optional
                .ofNullable(line)
                .map(s -> Arrays.asList(s.trim().split(COMMA))) // Separa por comas
                .orElseThrow(RuntimeException::new); // Si la línea es null, lanza excepción

        String portfolioOwner = splitValue.get(13);
        String pais = obtenerPais(options, portfolioOwner);


        out.output(new Apm(
                        splitValue.get(0), //apmCode
                        splitValue.get(1).toUpperCase(), //apmName
                        splitValue.get(2),  // isCompliant
                        splitValue.get(3),  // cia
                        splitValue.get(4), // lcState
                        LifeDate.create(splitValue.get(5)), // productionDate
                        LifeDate.create(splitValue.get(6)), // retirementDate
                        splitValue.get(7), // dbrRating
                        splitValue.get(8),  // applicationTested
                        splitValue.get(9), // applicationContact
                        splitValue.get(10), // manager
                        splitValue.get(11), // vp
                        splitValue.get(12), // svp
                        splitValue.get(13), // portfolioOwner
                        splitValue.get(14),  // iso
                        pais
                )
        );
    }

    private static String obtenerPais(JobOptions options, String portfolioOwner) {
        Map<String, String> countryMap = Map.of(
                options.getCl(), "CL",
                options.getCo(), "CO",
                options.getCca(), "CCA",
                options.getMx(), "MX",
                options.getPe(), "PE",
                options.getUy(), "UY",
                options.getIb(), "IB"
        );

        // Buscar el país correspondiente
        return countryMap.getOrDefault(portfolioOwner, null);
    }
}