package com.verix.landing.infrastructure.streaming.transformations;

import com.google.api.services.bigquery.model.TableRow;
import com.verix.landing.domain.model.Landing;
import org.apache.beam.sdk.transforms.DoFn;

public class ConvertLandingToTableRow  extends DoFn<Landing, TableRow> {
    @ProcessElement
    public void processElement(@Element Landing landing, OutputReceiver<TableRow> out) {
        out.output( new TableRow()
                .set("component_id", landing.getUniqueComponentId())
                .set("apm_code", landing.getApmCode())
                .set("app_name", landing.getAppName())
                .set("vendor", landing.getVendor())
                .set("sw_type", landing.getSwType())
                .set("sw_name", landing.getSwName())
                .set("sw_id", landing.getSwId())
                .set("sw_version", landing.getSwVersion())
                .set("sw_expire_in", landing.getSwExpireIn())
                .set("group_head_name", landing.getGroupHeadName())
                .set("business_lines", landing.getBusinessLines())
                .set("dbr_tier", landing.getDbrTier())
                .set("sw_valid_plan", landing.getSwValidPlan())
                .set("app_valid_plan", landing.getAppValidPlan())
                .set("sw_plan_status", landing.getSwPlanStatus())
                .set("plan_number", landing.getPlanNo())
                .set("plan_name", landing.getPlanName())
                .set("plan_start_date", landing.getPlanStartDate())
                .set("plan_finish_date", landing.getPlanEndDate())
                .set("plan_funded", landing.getPlanFunded())
                .set("ref_number", landing.getRefNumber())
                .set("plan_comments", landing.getPlanComments())
                .set("plan_external_cost", landing.getPlanExternalCost())
                .set("plan_internal_cost", landing.getPlanInternalCost())
                .set("plan_license_cost", landing.getPlanLicenseCost())
                .set("eos_date", landing.getEosDate())
                .set("extended_date", landing.getExtendedDate())
                .set("extended_custom_date", landing.getExtendedCustomDate())
                .set("local_rcmp", landing.getLocalRCMP())
                .set("country_name", landing.getCountryName())
                .set("internet_facing", landing.getInternetFacing())
                .set("us_flag", landing.getUsFlag())
                .set("lifecycle", landing.getLifecycle())
                .set("environments", landing.getEnvironments()));
    }
}
