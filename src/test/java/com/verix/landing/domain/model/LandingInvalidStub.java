package com.verix.landing.domain.model;

import java.util.HashMap;
import java.util.Map;

public final class LandingInvalidStub {

    public static final String EMPTY_STRING = "";
    public static final String INVALID_CHARS = "!¡'¿+ç´%*";
    public static final String NULL = null;

    public static Map<String, String> invalidCharsForRequiredFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", INVALID_CHARS);
        fields.put("apmCode", INVALID_CHARS);
        fields.put("appName", INVALID_CHARS);
        fields.put("vendor", INVALID_CHARS);
        fields.put("swType", INVALID_CHARS);
        fields.put("swName", INVALID_CHARS);
        fields.put("swId", INVALID_CHARS);
        fields.put("swVersion", INVALID_CHARS);
        fields.put("swExpireIn", INVALID_CHARS);
        fields.put("groupHeadName", INVALID_CHARS);
        fields.put("businessLines", INVALID_CHARS);
        fields.put("dbrTier", "Tier 2");
        fields.put("swValidPlan", INVALID_CHARS);
        fields.put("appValidPlan", INVALID_CHARS);
        fields.put("swPlanStatus", INVALID_CHARS);
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
        fields.put("localRCMP", INVALID_CHARS);
        fields.put("countryName", INVALID_CHARS);
        fields.put("internetFacing", INVALID_CHARS);
        fields.put("usFlag", INVALID_CHARS);
        fields.put("lifecycle", INVALID_CHARS);
        fields.put("environments", INVALID_CHARS);
        return fields;
    }

    public static Map<String, String> onlyOptionalFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", EMPTY_STRING);
        fields.put("apmCode", EMPTY_STRING);
        fields.put("appName", EMPTY_STRING);
        fields.put("vendor", EMPTY_STRING);
        fields.put("swType", EMPTY_STRING);
        fields.put("swName", EMPTY_STRING);
        fields.put("swId", EMPTY_STRING);
        fields.put("swVersion", EMPTY_STRING);
        fields.put("swExpireIn", EMPTY_STRING);
        fields.put("groupHeadName", EMPTY_STRING);
        fields.put("businessLines", EMPTY_STRING);
        fields.put("dbrTier", "Tier 2");
        fields.put("swValidPlan", EMPTY_STRING);
        fields.put("appValidPlan", EMPTY_STRING);
        fields.put("swPlanStatus", EMPTY_STRING);
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
        fields.put("localRCMP", EMPTY_STRING);
        fields.put("countryName", EMPTY_STRING);
        fields.put("internetFacing", EMPTY_STRING);
        fields.put("usFlag", EMPTY_STRING);
        fields.put("lifecycle", EMPTY_STRING);
        fields.put("environments", EMPTY_STRING);
        return fields;
    }

    public static Map<String, String> nullForRequiredFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("uniqueComponentId", NULL);
        fields.put("apmCode", NULL);
        fields.put("appName", NULL);
        fields.put("vendor", NULL);
        fields.put("swType", NULL);
        fields.put("swName", NULL);
        fields.put("swId", NULL);
        fields.put("swVersion", NULL);
        fields.put("swExpireIn", NULL);
        fields.put("groupHeadName", NULL);
        fields.put("businessLines", NULL);
        fields.put("dbrTier", "Tier 2");
        fields.put("swValidPlan", NULL);
        fields.put("appValidPlan", NULL);
        fields.put("swPlanStatus", NULL);
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
        fields.put("localRCMP", NULL);
        fields.put("countryName", NULL);
        fields.put("internetFacing", NULL);
        fields.put("usFlag", NULL);
        fields.put("lifecycle", NULL);
        fields.put("environments", NULL);
        return fields;
    }
}
