package com.verix.landing.domain.model;

import java.util.HashMap;
import java.util.Map;

public final class LandingValidStub {

    public static final String EXPECTED_DATE = "2027-01-12";
    public static final String EXPECTED_START_PLAN_DATE = "2025-11-01";
    public static final String EXPECTED_END_PLAN_DATE = "2026-10-31";
    public static final String EMPTY_STRING = "";
    public static final String INVALID_CHARS = "!¡'¿+ç´%*";

    public static Map<String, String> allFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", "10036/45578");
        fields.put("apmCode", "B8RW");
        fields.put("appName", "Intranet Portal - SBP");
        fields.put("vendor", "Microsoft");
        fields.put("swType", "Operating System");
        fields.put("swName", "Windows Server");
        fields.put("swId", "45578");
        fields.put("swVersion", "Windows Server 2016 Standard 10.0.14393.5502");
        fields.put("swExpireIn", ">36 Months");
        fields.put("groupHeadName", "Francisco Aristeguieta Silva");
        fields.put("businessLines", "Peru");
        fields.put("dbrTier", "Tier 2");
        fields.put("swValidPlan", "Yes");
        fields.put("appValidPlan", "No");
        fields.put("swPlanStatus", "Draft");
        fields.put("planNo", "38860");
        fields.put("planName", "DES-WS2016-PG");
        fields.put("planStartDate", "11/01/2025");
        fields.put("planEndDate", "10/31/2026");
        fields.put("planFunded", "Yes");
        fields.put("refNumber", "WS2016");
        fields.put("planComments", "En revisión.");
        fields.put("planExternalCost", "0");
        fields.put("planInternalCost", "0");
        fields.put("planLicenseCost", "0");
        fields.put("eosDate", "01/12/2027");
        fields.put("extendedDate", "01/12/2027");
        fields.put("extendedCustomDate", "01/12/2027");
        fields.put("localRCMP", "No");
        fields.put("countryName", "Peru");
        fields.put("internetFacing", "No");
        fields.put("usFlag", "false");
        fields.put("lifecycle", "Production");
        fields.put("environments", "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev");
        return fields;
    }

    public static Map<String, String> onlyRequiredFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", "10036/45578");
        fields.put("apmCode", "B8RW");
        fields.put("appName", "Intranet Portal - SBP");
        fields.put("vendor", "Microsoft");
        fields.put("swType", "Operating System");
        fields.put("swName", "Windows Server");
        fields.put("swId", "45578");
        fields.put("swVersion", "Windows Server 2016 Standard 10.0.14393.5502");
        fields.put("swExpireIn", ">36 Months");
        fields.put("groupHeadName", "Francisco Aristeguieta Silva");
        fields.put("businessLines", "Peru");
        fields.put("dbrTier", EMPTY_STRING);
        fields.put("swValidPlan", "Yes");
        fields.put("appValidPlan", "No");
        fields.put("swPlanStatus", "Missing");
        fields.put("planNo", EMPTY_STRING);
        fields.put("planName", EMPTY_STRING);
        fields.put("planStartDate", EMPTY_STRING);
        fields.put("planEndDate", EMPTY_STRING);
        fields.put("planFunded", EMPTY_STRING);
        fields.put("refNumber", EMPTY_STRING);
        fields.put("planComments", EMPTY_STRING);
        fields.put("planExternalCost", EMPTY_STRING);
        fields.put("planInternalCost", EMPTY_STRING);
        fields.put("planLicenseCost", EMPTY_STRING);
        fields.put("eosDate", EMPTY_STRING);
        fields.put("extendedDate", EMPTY_STRING);
        fields.put("extendedCustomDate", EMPTY_STRING);
        fields.put("localRCMP", "No");
        fields.put("countryName", "Peru");
        fields.put("internetFacing", "No");
        fields.put("usFlag", "false");
        fields.put("lifecycle", "Production");
        fields.put("environments", "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev");
        return fields;
    }

    public static Map<String, String> invalidCharsForOptional() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", "10036/45578");
        fields.put("apmCode", "B8RW");
        fields.put("appName", "Intranet Portal - SBP");
        fields.put("vendor", "Microsoft");
        fields.put("swType", "Operating System");
        fields.put("swName", "Windows Server");
        fields.put("swId", "45578");
        fields.put("swVersion", "Windows Server 2016 Standard 10.0.14393.5502");
        fields.put("swExpireIn", ">36 Months");
        fields.put("groupHeadName", "Francisco Aristeguieta Silva");
        fields.put("businessLines", "Peru");
        fields.put("dbrTier", INVALID_CHARS);
        fields.put("swValidPlan", "Yes");
        fields.put("appValidPlan", "No");
        fields.put("swPlanStatus", "Missing");
        fields.put("planNo", INVALID_CHARS);
        fields.put("planName", INVALID_CHARS);
        fields.put("planStartDate", INVALID_CHARS);
        fields.put("planEndDate", INVALID_CHARS);
        fields.put("planFunded", INVALID_CHARS);
        fields.put("refNumber", INVALID_CHARS);
        fields.put("planComments", INVALID_CHARS);
        fields.put("planExternalCost", INVALID_CHARS);
        fields.put("planInternalCost", INVALID_CHARS);
        fields.put("planLicenseCost", INVALID_CHARS);
        fields.put("eosDate", INVALID_CHARS);
        fields.put("extendedDate", INVALID_CHARS);
        fields.put("extendedCustomDate", INVALID_CHARS);
        fields.put("localRCMP", "No");
        fields.put("countryName", "Peru");
        fields.put("internetFacing", "No");
        fields.put("usFlag", "false");
        fields.put("lifecycle", "Production");
        fields.put("environments", "Prod,DR,UAT/QAT/Pre-PROD,IST,Dev");
        return fields;
    }
}