package com.verix.forecast.application.service.request;

import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.domain.model.Remediation;

import java.util.List;

public final class ForecastRequest {
    private final List<Remediation> remediationList;
    private final List<Portfolio>   portfolioList;

    private ForecastRequest(List<Remediation> remediationList, List<Portfolio> portfolioList) {
        this.remediationList = remediationList;
        this.portfolioList = portfolioList;
    }

    public static ForecastRequest of(List<Remediation> remediationList, List<Portfolio> portfolioList) {
        return new ForecastRequest(remediationList, portfolioList);
    }

    public List<Remediation> getRemediationList() {
        return remediationList;
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }
}
