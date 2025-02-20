package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidPropertyException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Remediation implements Serializable {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s._,-@#$/]";
    private static final String REPLACEMENT = "";

    private final String strategy;
    private final Country countryCode;
    private final String apmCode;
    private final String component;
    private final String version;
    private final Action action;
    private final String newVersion;
    private final DeliveryDate deliveryDate;

    public Remediation(String strategy, Country countryCode, String apmCode, String component, String version, Action action, String newVersion, DeliveryDate deliveryDate) {
        this.strategy     = setStrategy(strategy);
        this.countryCode  = setCountryCode(countryCode);
        this.apmCode      = setApmCode(apmCode);
        this.component    = setComponent(component);
        this.version      = setVersion(version);
        this.action       = setAction(action);
        this.newVersion   = setNewVersion(newVersion);
        this.deliveryDate = setDeliveryDate(deliveryDate);
    }

    public String getStrategy() {
        return strategy;
    }

    private String setStrategy(String strategy) {
        return removeSpecialCharsForRequired(strategy);
    }

    public Country setCountryCode(Country countryCode) {
        return countryCode;
    }

    public Country getCountryCode() {
        return countryCode;
    }

    public String getApmCode() {
        return apmCode;
    }

    private String setApmCode(String apmCode) {
        return removeSpecialCharsForRequired(apmCode);
    }

    public String getComponent() {
        return component;
    }

    private String setComponent(String component) {
        return removeSpecialCharsForRequired(component);
    }

    public String getVersion() {
        return version;
    }

    private String setVersion(String version) {
        return removeSpecialCharsForRequired(version);
    }

    public Action getAction() {
        return action;
    }

    private Action setAction(Action action) {
        return action;
    }

    public String getNewVersion() {
        return newVersion;
    }

    private String setNewVersion(String newVersion) {
        return removeSpecialCharsForOptional(newVersion);
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    private DeliveryDate setDeliveryDate(DeliveryDate deliveryDate) {
        return deliveryDate;
    }

    private String removeSpecialCharsForRequired(String value) {
        String optionalValue = Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidPropertyException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT);

        if (optionalValue.isEmpty()) {
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
                                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT))
                .filter(Predicate.not(String::isEmpty))
                .orElse(null);

    }

    @Override
    public String toString() {
        return "Remediation{" +
                "strategy='" + strategy + '\'' +
                ", countryCode=" + countryCode.getValue() +
                ", apmCode='" + apmCode + '\'' +
                ", component='" + component + '\'' +
                ", version='" + version + '\'' +
                ", action=" + action.getValue() +
                ", newVersion='" + newVersion + '\'' +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
