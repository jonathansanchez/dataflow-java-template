package com.verix.forecast.domain.model;

import java.util.List;

public interface DataPipelineStrategy {
    List<Remediation> runStrategy();
    List<Portfolio> runCurrentStateForecast(String countryCode);
}
