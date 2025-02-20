package com.verix.forecast.application.service;

import com.verix.forecast.application.service.request.ForecastRequest;
import com.verix.forecast.domain.model.DataPipelineStrategy;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.domain.model.Remediation;
import com.verix.forecast.domain.model.exception.InvalidCountryException;

import java.util.List;
import java.util.Optional;

public class StreamingService {
    private final DataPipelineStrategy pipeline;
    private final ForecastService      forecastService;

    public StreamingService(DataPipelineStrategy pipeline, ForecastService forecastService) {
        this.pipeline        = pipeline;
        this.forecastService = forecastService;
    }

    public void execute() {
        List<Remediation> remediationList = pipeline.runStrategy();
        List<Portfolio>   portfolioList   = pipeline.runCurrentStateForecast(getCountryCode(remediationList));

        forecastService.execute(
                ForecastRequest.of(remediationList, portfolioList)
        );
    }

    private String getCountryCode(List<Remediation> remediationList) {
        Optional<String> value = Optional.ofNullable(remediationList
                .stream()
                .findFirst()
                .get()
                .getCountryCode()
                .getValue());

        if (value.isPresent()) {
            return value.get();
        }

        throw InvalidCountryException.thrown();
    }
}
