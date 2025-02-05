package com.verix.landing.domain.model;

import com.verix.landing.domain.model.exception.InvalidPropertyException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class Landing implements Serializable {

    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy").withLocale(Locale.ROOT);
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final String EMPTY_STRING = "";
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s._,-@#$/()&áéíóúñüÁÉÍÓÚÑÜ]";
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    String uniqueComponentId;
    String apmCode;
    String appName;
    String vendor;
    String swType;
    String swName;
    Integer swId;
    String swVersion;
    String swExpireIn;
    String groupHeadName;
    String businessLines;
    String dbrTier;
    Boolean swValidPlan;
    Boolean appValidPlan;
    String swPlanStatus;
    Integer planNo;
    String planName;
    String planStartDate;
    String planEndDate;
    Boolean planFunded;
    String refNumber;
    String planComments;
    Integer planExternalCost;
    Integer planInternalCost;
    Integer planLicenseCost;
    String eosDate;
    String extendedDate;
    String extendedCustomDate;
    Boolean localRCMP;
    String countryName;
    Boolean internetFacing;
    Boolean usFlag;
    String lifecycle;
    String environments;


    public Landing(String uniqueComponentId, String apmCode, String appName, String vendor, String swType, String swName, String swId, String swVersion, String swExpireIn, String groupHeadName, String businessLines, String dbrTier, String swValidPlan, String appValidPlan, String swPlanStatus, String planNo, String planName, String planStartDate, String planEndDate, String planFunded, String refNumber, String planComments, String planExternalCost, String planInternalCost, String planLicenseCost, String eosDate, String extendedDate, String extendedCustomDate, String localRCMP, String countryName, String internetFacing, String usFlag, String lifecycle, String environments) {
        this.uniqueComponentId = setUniqueComponentId(uniqueComponentId);
        this.apmCode = setApmCode(apmCode);
        this.appName = setAppName(appName);
        this.vendor = setVendor(vendor);
        this.swType = setSwType(swType);
        this.swName = setSwName(swName);
        this.swId = setSwId(swId);
        this.swVersion = setSwVersion(swVersion);
        this.swExpireIn = setSwExpireIn(swExpireIn);
        this.groupHeadName = setGroupHeadName(groupHeadName);
        this.businessLines = setBusinessLines(businessLines);
        this.dbrTier = setDbrTier(dbrTier);
        this.swValidPlan = setSwValidPlan(swValidPlan);
        this.appValidPlan = setAppValidPlan(appValidPlan);
        this.swPlanStatus = setSwPlanStatus(swPlanStatus);
        this.planNo = setPlanNo(planNo);
        this.planName = setPlanName(planName);
        this.planStartDate = setPlanStartDate(planStartDate);
        this.planEndDate = setPlanEndDate(planEndDate);
        this.planFunded = setPlanFunded(planFunded);
        this.refNumber = setRefNumber(refNumber);
        this.planComments = setPlanComments(planComments);
        this.planExternalCost = setPlanExternalCost(planExternalCost);
        this.planInternalCost = setPlanInternalCost(planInternalCost);
        this.planLicenseCost = setPlanLicenseCost(planLicenseCost);
        this.eosDate = setEosDate(eosDate);
        this.extendedDate = setExtendedDate(extendedDate);
        this.extendedCustomDate = setExtendedCustomDate(extendedCustomDate);
        this.localRCMP = setLocalRCMP(localRCMP);
        this.countryName = setCountryName(countryName);
        this.internetFacing = setInternetFacing(internetFacing);
        this.usFlag = setUsFlag(usFlag);
        this.lifecycle = setLifecycle(lifecycle);
        this.environments = setEnvironments(environments);
    }

    public String getUniqueComponentId() {
        return uniqueComponentId;
    }

    public String getApmCode() {
        return apmCode;
    }

    public String getAppName() {
        return appName;
    }

    public String getVendor() {
        return vendor;
    }

    public String getSwType() {
        return swType;
    }

    public String getSwName() {
        return swName;
    }

    public Integer getSwId() {
        return swId;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public String getSwExpireIn() {
        return swExpireIn;
    }

    public String getGroupHeadName() {
        return groupHeadName;
    }

    public String getBusinessLines() {
        return businessLines;
    }

    public String getDbrTier() {
        return dbrTier;
    }

    public Boolean getSwValidPlan() {
        return swValidPlan;
    }

    public Boolean getAppValidPlan() {
        return appValidPlan;
    }

    public String getSwPlanStatus() {
        return swPlanStatus;
    }

    public String getPlanName() {
        return planName;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public Boolean getPlanFunded() {
        return planFunded;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public String getPlanComments() {
        return planComments;
    }

    public Integer getPlanExternalCost() {
        return planExternalCost;
    }

    public Integer getPlanInternalCost() {
        return planInternalCost;
    }

    public Integer getPlanLicenseCost() {
        return planLicenseCost;
    }

    public String getEosDate() {
        return eosDate;
    }

    public String getExtendedDate() {
        return extendedDate;
    }

    public String getExtendedCustomDate() {
        return extendedCustomDate;
    }

    public Boolean getLocalRCMP() {
        return localRCMP;
    }

    public String getCountryName() {
        return countryName;
    }

    public Boolean getInternetFacing() {
        return internetFacing;
    }

    public Boolean getUsFlag() {
        return usFlag;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public String getEnvironments() {
        return environments;
    }

    public String setUniqueComponentId(String uniqueComponentId) {
        return removeSpecialCharsForRequired(uniqueComponentId);
    }

    public String setApmCode(String apmCode) {
        return removeSpecialCharsForRequired(apmCode);
    }

    public String setAppName(String appName) {
        return removeSpecialCharsForRequired(appName);
    }

    public String setVendor(String vendor) {
        return removeSpecialCharsForRequired(vendor);
    }

    public String setSwType(String swType) {
        return removeSpecialCharsForRequired(swType);
    }

    public String setSwName(String swName) {
        return removeSpecialCharsForRequired(swName);
    }

    public Integer setSwId(String swId) {
        return Integer.parseInt(removeSpecialCharsForRequired(swId));
    }

    public String setSwVersion(String swVersion) {
        return removeSpecialCharsForRequired(swVersion);
    }

    public String setSwExpireIn(String swExpireIn) {
        return removeSpecialCharsForRequired(swExpireIn);
    }

    public String setGroupHeadName(String groupHeadName) {
        return removeSpecialCharsForRequired(groupHeadName);
    }

    public String setBusinessLines(String businessLines) {
        return removeSpecialCharsForRequired(businessLines);
    }

    public String setDbrTier(String dbrTier) {
        return removeSpecialCharsForOptional(dbrTier);
    }

    public Boolean setSwValidPlan(String swValidPlan) {
        return parseBooleanForRequired(swValidPlan);
    }

    public Boolean setAppValidPlan(String appValidPlan) {
        return parseBooleanForRequired(appValidPlan);
    }

    public String setSwPlanStatus(String swPlanStatus) {
        return removeSpecialCharsForRequired(swPlanStatus);
    }

    public String setPlanName(String planName) {
        return removeSpecialCharsForOptional(planName);
    }

    public String setPlanStartDate(String planStartDate) {
        return parseDate(planStartDate);
    }

    public String setPlanEndDate(String planEndDate) {
        return parseDate(planEndDate);
    }

    public Boolean setPlanFunded(String planFunded) {
        return parseBooleanForOptional(planFunded);
    }

    public String setRefNumber(String refNumber) {
        return removeSpecialCharsForOptional(refNumber);
    }

    public String setPlanComments(String planComments) {
        return removeSpecialCharsForOptional(planComments);
    }

    public Integer setPlanExternalCost(String planExternalCost) {
        return parseIntegerForOptional(planExternalCost);
    }

    public Integer setPlanInternalCost(String planInternalCost) {
        return parseIntegerForOptional(planInternalCost);
    }

    public Integer setPlanLicenseCost(String planLicenseCost) {
        return parseIntegerForOptional(planLicenseCost);
    }

    public String setEosDate(String eosDate) {
        return parseDate(eosDate);
    }

    public String setExtendedDate(String extendedDate) {
        return parseDate(extendedDate);
    }

    public String setExtendedCustomDate(String extendedCustomDate) {
        return parseDate(extendedCustomDate);
    }

    public Boolean setLocalRCMP(String localRCMP) {
        return parseBooleanForRequired(localRCMP);
    }

    public String setCountryName(String countryName) {
        return removeSpecialCharsForRequired(countryName);    }

    public Boolean setInternetFacing(String internetFacing) {
        return parseBooleanForRequired(internetFacing);
    }

    public Boolean setUsFlag(String usFlag) {
        return parseBooleanForRequired(usFlag);
    }

    public String setLifecycle(String lifecycle) {
        return removeSpecialCharsForRequired(lifecycle);    }

    public String setEnvironments(String environments) {
        return removeSpecialCharsForRequired(environments);
    }

    public Integer getPlanNo() {
        return planNo;
    }

    public Integer setPlanNo(String planNo) {
        return parseIntegerForOptional(planNo);
    }

    private String parseDate(String date){
        return Optional
                .ofNullable(removeSpecialCharsForOptional(date))
                .filter(Predicate.not(String::isEmpty))
                .map(s -> {
                    LocalDate formattedDate = LocalDate.parse(s.trim(), INPUT_DATE_FORMAT);
                    return formattedDate.format(OUTPUT_DATE_FORMAT);
                }).
                orElse(null);
    }

    private String removeSpecialCharsForRequired(String value){
        String optionalValue =  Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidPropertyException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING);

        if(optionalValue.isEmpty()){
            throw InvalidPropertyException.thrown();
        }
        return optionalValue;

    }

    private String removeSpecialCharsForOptional(String value) {
        return Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .map(s ->
                        s
                                .trim()
                                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING))
                .filter(Predicate.not(String::isEmpty))
                .orElse(null);

    }

    private Boolean parseBooleanForRequired(String value){
        return switch(removeSpecialCharsForRequired(value).toLowerCase()){
            case YES, TRUE -> true;
            case NO, FALSE -> false;
            default -> throw new IllegalStateException("Unexpected value to parse as Boolean: " + value);
        };
    }

    private Boolean parseBooleanForOptional(String value){
        return Optional.ofNullable(removeSpecialCharsForOptional(value))
                .map(s -> {
                    String lowerCase = s.toLowerCase();
                    return switch(lowerCase){
                        case YES, TRUE -> true;
                        case NO, FALSE -> false;
                        default -> null;
                    };
                }).orElse(null);

    }

    private Integer parseIntegerForOptional(String value){
        return Optional
                .ofNullable(removeSpecialCharsForOptional(value))
                .map(Integer::parseInt)
                .orElse(null);
    }
}
