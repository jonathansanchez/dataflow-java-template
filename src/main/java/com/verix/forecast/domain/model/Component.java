package com.verix.forecast.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Component implements Serializable {
    private static final DateTimeFormatter FORMAT_EOL = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final int YEARS_TO_ADD = 2;

    private final String country;
    private final String apmCode;
    private final String name;
    private final Eol eol;

    public Component(String country, String apmCode, String name, Eol eol) {
        this.country = country;
        this.apmCode = apmCode;
        this.name = name;
        this.eol = eol;
    }

    public String getCountry() {
        return country;
    }

    public String getApmCode() {
        return apmCode;
    }

    public String getName() {
        return name;
    }

    public Eol getEol() {
        return eol;
    }

    public Boolean hasEol() {
        return eol.getValue() != null;
    }

    public  Boolean eolIsExpired(LocalDate portfolioDate) {
        LocalDate date = LocalDate.parse(LocalDate.parse(eol.getValue()).format(FORMAT_EOL));
        return date.isEqual(portfolioDate) || date.isBefore(portfolioDate);
    }

    public Boolean eolIsBetweenTwoYears(LocalDate portfolioDate) {
        LocalDate date = LocalDate.parse(LocalDate.parse(eol.getValue()).format(FORMAT_EOL));
        return date.isAfter(portfolioDate)
                && !date.isEqual(portfolioDate)
                && (date.isBefore(portfolioDate.plusYears(YEARS_TO_ADD)) || date.isEqual(portfolioDate.plusYears(YEARS_TO_ADD)));
    }

    @Override
    public String toString() {
        return "Component{" +
                "country='" + country + '\'' +
                ", apmCode='" + apmCode + '\'' +
                ", name='" + name + '\'' +
                ", eol=" + eol.getValue() +
                '}';
    }
}
