package com.verix.apm.infrastructure.streaming.transformation;

//public class ApmToTableRow
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
                .set("iso",apm.getIso());
        context.output(row);
        System.out.println("Datos preparados: " + row.toString());
    }
}