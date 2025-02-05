package com.verix.apm.domain.model;

import com.verix.apm.domain.model.exception.InvalidPropertyException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

//Atributos/Priopiedades
public class Apm implements Serializable {
    //private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s.,-@#$/]";
    //private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s.,-@#$/áéíóúÁÉÍÓÚñÑ]";
    //private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s(),.-@#$/áéíóúÁÉÍÓÚñÑ]";
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s(),.-@#$/áéíóúÁÉÍÓÚñÑ_]";
    private static final String REPLACEMENT = "";
    private static final String EMPTY_REPLACEMENT = " ";


    private final String apmCode;
    private final String apmName;
    private final Boolean isCompliant;
    private final Boolean cia;
    private final String lcState;
    private final LifeDate productionDate;
    private final LifeDate retirementDate;
    private final String dbrRating;
    private final Boolean applicationTested;
    private final String applicationContact;
    private final String manager;
    private final String vp;
    private final String svp;
    private final String portfolioOwner;
    private final String iso;
    private final String country;

    //Constructor: recibe todos los valores - Asigna valores
/*    public Apm(String apmCode, String apmName, Boolean isCompliant, Boolean cia, String lcState, LifeDate productionDate, LifeDate retirementDate, String dbrRating, Boolean applicationTested, String applicationContact, String manager, String vp, String svp, String portfolioOwner, String iso) {
        this.apmCode = StringCleaner.removeSpecialCharsForRequired(apmCode);
        this.apmName = StringCleaner.removeSpecialCharsForOptional(apmName);
        this.isCompliant = BooleanCleaner.parseBoolean(String.valueOf(isCompliant));
        this.cia = BooleanCleaner.parseBoolean(String.valueOf(cia));
        this.lcState = StringCleaner.removeSpecialCharsForOptional(lcState);
        this.productionDate = productionDate;
        this.retirementDate = retirementDate;
        this.dbrRating = StringCleaner.removeSpecialCharsForOptional(dbrRating);
        this.applicationTested = BooleanCleaner.parseBoolean(String.valueOf(applicationTested));
        this.applicationContact = StringCleaner.removeSpecialCharsForRequired(applicationContact);
        this.manager = StringCleaner.removeSpecialCharsForOptional(manager);
        this.vp = StringCleaner.removeSpecialCharsForRequired(vp);
        this.svp = StringCleaner.removeSpecialCharsForOptional(svp);
        this.portfolioOwner = StringCleaner.removeSpecialCharsForOptional(portfolioOwner);
        this.iso = StringCleaner.removeSpecialCharsForOptional(iso);

        public String getApmCode() { return apmCode; }
        public String getApmName() { return apmName; }
        public Boolean getIsCompliant() { return isCompliant; }
        public Boolean getCia() { return cia; }
        public String getLcState() { return lcState; }
        public LifeDate getProductionDate() { return productionDate; }
        public LifeDate getRetirementDate() { return retirementDate; }
        public String getDbrRating() { return dbrRating; }
        public Boolean getApplicationTested() { return applicationTested; }
        public String getApplicationContact() { return applicationContact; }
        public String getManager() { return manager; }
        public String getVp() { return vp; }
        public String getSvp() { return svp; }
        public String getPortfolioOwner() { return portfolioOwner; }
        public String getIso() { return iso; }
    }*/

        public Apm(String apmCode, String apmName, String isCompliant, String cia, String lcState, LifeDate productionDate, LifeDate retirementDate, String dbrRating, String applicationTested, String applicationContact, String manager, String vp, String svp, String portfolioOwner, String iso, String country) {
        this.apmCode = setApmCode(apmCode);
        this.apmName = setApmName(apmName);
        this.isCompliant = setIsCompliant(isCompliant);
        this.cia = setCia(cia);
        this.lcState = setLcState(lcState);
        this.productionDate = productionDate;
        this.retirementDate = retirementDate;
        this.dbrRating = setDbrRating(dbrRating);
        this.applicationTested = setApplicationTested(applicationTested);
        this.applicationContact = setApplicationContact(applicationContact);
        this.manager = setManager(manager);
        this.vp = setVp(vp);
        this.svp = setSvp(svp);
        this.portfolioOwner = setPortfolioOwner(portfolioOwner);
        this.iso = setIso(iso);
        this.country=country;
    }


    // Los métodos setXX aplican una limpieza antes de la asignación en el constructor.
    public String getApmCode() { return apmCode; }
    public String setApmCode(String apmCode){return removeSpecialCharsForRequired(apmCode);}

    public String getApmName() { return apmName; }
    public String setApmName(String apmName){return removeSpecialCharsForOptional(apmName);}

    public Boolean getIsCompliant() { return isCompliant; }
    public Boolean setIsCompliant(String isCompliant){return BooleanCleaner(isCompliant);}

    public Boolean getCia() { return cia; }
    public Boolean setCia(String cia){return BooleanCleaner(cia);}

    public String getLcState() { return lcState; }
    public String setLcState(String lcState){return removeSpecialCharsForOptional(lcState);}

    public LifeDate getProductionDate() { return productionDate; }
    //public LifeDate setProductionDate(LifeDate productionDate){return productionDate;}

    public LifeDate getRetirementDate() { return retirementDate; }

    public String getDbrRating() { return dbrRating; }
    public String setDbrRating(String dbrRating){return removeSpecialCharsForOptional(dbrRating);}

    public Boolean getApplicationTested() { return applicationTested; }
    public Boolean setApplicationTested(String applicationTested){return BooleanCleaner(applicationTested);}

    public String getApplicationContact() { return applicationContact; }
    public String setApplicationContact(String applicationContact){return removeSpecialCharsForRequired(applicationContact);}

    public String getManager() { return manager; }
    public String setManager(String manager){return removeSpecialCharsForOptional(manager);}

    public String getVp() { return vp; }
    public String setVp(String vp){return removeSpecialCharsForRequired(vp);}

    public String getSvp() { return svp; }
    public String setSvp(String svp){return removeSpecialCharsForOptional(svp);}

    public String getPortfolioOwner() { return portfolioOwner; }
    public String setPortfolioOwner(String portfolioOwner){return removeSpecialCharsForOptional(portfolioOwner);}

    public String getIso() { return iso; }
    public String setIso(String iso){return removeSpecialCharsForOptional(iso);}

    public String getCountry(){return country;}



    private String removeSpecialCharsForRequired(String value) {
        return Optional
                .ofNullable(value)
                .orElseThrow(InvalidPropertyException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT)
                .replaceAll("-", EMPTY_REPLACEMENT)
                .replaceAll("_", EMPTY_REPLACEMENT)
                .replaceAll(",", REPLACEMENT);
    }

    private String removeSpecialCharsForOptional(String value) {
        return Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty)) // Filtra valores vacíos ("")
                .map(s -> s.trim()
                        .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT)
                        .replaceAll("_", EMPTY_REPLACEMENT)
                        .replaceAll("-", EMPTY_REPLACEMENT)
                        .replaceAll(",", REPLACEMENT))
                .filter(s -> !s.equalsIgnoreCase("NULL"))
                .orElse(null);

    }

    private static Boolean BooleanCleaner(String value) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String lowerCase = s.toLowerCase();
                    return switch (lowerCase) {
                        case "yes", "true" -> true;
                        case "no", "false" -> false;
                        default -> null;
                    };
                })
                .orElse(null);
    }
}
