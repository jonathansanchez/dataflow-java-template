package com.verix.apm.infrastructure.streaming.transformation;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.apm.domain.model.Apm;
import org.apache.beam.sdk.transforms.DoFn;


public class ApmToTableRow extends DoFn<Apm, TableRow> {

    @ProcessElement
    public void processElement(ProcessContext context) {
        Apm apm = context.element();
        TableRow row = new TableRow()
                .set("apm_code", apm.getApmCode())
                .set("apm_name", apm.getApmName())
                .set("is_compliant", apm.getIsCompliant())
                .set("cia", apm.getCia())
                .set("lc_state", apm.getLcState())
                .set("production_date", apm.getProductionDate().getValue())
                .set("retirement_date", apm.getRetirementDate().getValue())
                .set("dbr_rating", apm.getDbrRating())
                .set("application_tested", apm.getApplicationTested())
                .set("application_contact", apm.getApplicationContact())
                .set("manager", apm.getManager())
                .set("vp", apm.getVp())
                .set("svp", apm.getSvp())
                .set("portfolio_owner", apm.getPortfolioOwner())
                .set("iso",apm.getIso())
                .set("country",apm.getCountry());
        context.output(row);
        System.out.println("Datos preparados: " + row.toString());
    }

/*    private String obtenerPais(String portfolioOwner) {
        return PAISES.getOrDefault(portfolioOwner, null);
        // LLAMALO .set("pais", obtenerPais(apm.getPortfolioOwner()));
    }*/

/*    private static final Map<String, String> PAISES = Map.of(
            "Francisco Palma Maturana", "CL",
            "David Conboy", "CCA",
            "Danilo Gónzalez Asensio", "CO",
            "Edgar Itsvan Cuevas Inesa","MX",
            "Jose Javier Ortiz Fuentes","PE",
            "Luis Daniel Ivaldi Arellano","UY",
            "Luis Alfredo Gonzalez Quiroz","IB"// Puedes agregar más casos aquí
    );*/

}