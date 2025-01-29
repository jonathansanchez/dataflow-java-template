package com.verix.sam.domain.model;

public final class SamStub {
    public static Sam create() {
        return new Sam(
                "Microsoft",
                "Data Integration",
                "SQL Server Integration Services",
                "SQL Server Integration Services 2014 Standard 12.0.2456.0",
                "2014",
                "12.0.2456.0",
                "Standard",
                null,
                null,
                LifeDate.create("12/17/2014 12:00:00 AM"),
                null,
                LifeDate.create("7/9/2024 12:00:00 AM"),
                null,
                "samp_sw_product");
    }
}