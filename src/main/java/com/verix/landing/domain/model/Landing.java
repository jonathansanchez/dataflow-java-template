package com.verix.landing.domain.model;

import java.io.Serializable;

public class Landing implements Serializable {

    String uniqueComponentId;
    String apmCode;
    String appName;
    String vendor;
    String softwareType;
    String softwareName;

    public Landing(String uniqueComponentId, String apmCode, String appName, String vendor, String softwareType, String softwareName) {
        this.uniqueComponentId = uniqueComponentId;
        this.apmCode = apmCode;
        this.appName = appName;
        this.vendor = vendor;
        this.softwareType = softwareType;
        this.softwareName = softwareName;
    }

    public String getUniqueComponentId() {
        return uniqueComponentId;
    }

    public void setUniqueComponentId(String uniqueComponentId) {
        this.uniqueComponentId = uniqueComponentId;
    }

    public String getApmCode() {
        return apmCode;
    }

    public void setApmCode(String apmCode) {
        this.apmCode = apmCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSoftwareType() {
        return softwareType;
    }

    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }
}
