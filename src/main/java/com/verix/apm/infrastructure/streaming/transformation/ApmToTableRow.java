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
                .set("apmCode", apm.getApmCode())
                .set("apmName", apm.getApmName())
                .set("isCompliant", apm.getIsCompliant())
                .set("cia", apm.getCia())
                .set("lcState", apm.getLcState())
                .set("productionDate", apm.getProductionDate().getValue())
                .set("retirementDate", apm.getRetirementDate().getValue())
                .set("dbrRating", apm.getDbrRating())
                .set("applicationTested", apm.getApplicationTested())
                .set("applicationContact", apm.getApplicationContact())
                .set("manager", apm.getManager())
                .set("vp", apm.getVp())
                .set("svp", apm.getSvp())
                .set("portfolioOwner", apm.getPortfolioOwner())
                .set("iso",apm.getIso());
        context.output(row);
    }
}