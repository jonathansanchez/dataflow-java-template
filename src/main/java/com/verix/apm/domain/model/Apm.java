package com.verix.apm.domain.model;

//Atributos/Priopiedades
public class Apm {
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

    //Constructor: recibe todos los valores - Asigna valores
    public Apm(String apmCode, String apmName, Boolean isCompliant, Boolean cia, String lcState, LifeDate productionDate, LifeDate retirementDate, String dbrRating, Boolean applicationTested, String applicationContact, String manager, String vp, String svp, String portfolioOwner, String iso) {
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
    }

    // Métodos Getters: permiten acceder a los atributos de la clase
    // Los métodos setXX aplican una limpieza de caracteres especiales antes de la asignación en el constructor.
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

}
