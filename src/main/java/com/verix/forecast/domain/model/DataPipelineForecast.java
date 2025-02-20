package com.verix.forecast.domain.model;

import java.util.List;

public interface DataPipelineForecast {
    void run(List<Portfolio> portfolioList);
}
