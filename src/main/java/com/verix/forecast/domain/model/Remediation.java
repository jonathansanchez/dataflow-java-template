package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidPropertyException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Remediation implements Serializable {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s._,-@#$/]";
    private static final String REPLACEMENT = "";

    private final String strategy;
    private final String apmCode;
    private final String component;
    private final String version;
    private final Action action;
    private final String newVersion;
    private final DeliveryDate deliveryDate;

    public Remediation(String strategy, String apmCode, String component, String version, Action action, String newVersion, DeliveryDate deliveryDate) {
        this.strategy = setStrategy(strategy);
        this.apmCode = setApmCode(apmCode);
        this.component = setComponent(component);
        this.version = setVersion(version);
        this.action = setAction(action);
        this.newVersion = setNewVersion(newVersion);
        this.deliveryDate = setDeliveryDate(deliveryDate);
    }

    public String getStrategy() {
        return strategy;
    }

    private String setStrategy(String strategy) {
        return removeSpecialCharsForRequired(strategy);
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
        return removeSpecialCharsForRequired(newVersion);
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
}
