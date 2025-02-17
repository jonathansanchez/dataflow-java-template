package com.verix.forecast.domain.model;

import java.io.Serializable;

public class Component implements Serializable {
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
