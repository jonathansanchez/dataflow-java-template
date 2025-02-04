package com.verix.apm.infrastructure.streaming.transformation;

import com.verix.apm.domain.model.LifeDate;
import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
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


/*        // Imprimir valores después de conversión
        System.out.println("✅ Valores convertidos:");
        System.out.println(" - isCompliant: " + BooleanCleaner.parseBoolean(splitValue.get(2)));
        System.out.println(" - cia: " + BooleanCleaner.parseBoolean(splitValue.get(3)));
        System.out.println(" - applicationTested: " + BooleanCleaner.parseBoolean(splitValue.get(8)));*/

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
        if (options.getCl() != null && options.getCl().equalsIgnoreCase(portfolioOwner)) {
            return "CL";
        } else if (options.getCo() != null && options.getCo().equalsIgnoreCase(portfolioOwner)) {
            return "CO";
        } else if (options.getCca() != null && options.getCca().equalsIgnoreCase(portfolioOwner)) {
            return "CCA";
        } else if (options.getMx() != null && options.getMx().equalsIgnoreCase(portfolioOwner)) {
            return "MX";
        } else if (options.getPe() != null && options.getPe().equalsIgnoreCase(portfolioOwner)) {
            return "PE";
        } else if (options.getUy() != null && options.getUy().equalsIgnoreCase(portfolioOwner)) {
            return "UY";
        } else if (options.getIb() != null && options.getIb().equalsIgnoreCase(portfolioOwner)) {
            return "IB";
        }
        return null;  // Retorna null si no se encuentra un país válido
    }
}