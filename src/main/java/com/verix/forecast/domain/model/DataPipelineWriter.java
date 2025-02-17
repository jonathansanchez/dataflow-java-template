package com.verix.forecast.domain.model;

import java.util.List;

public interface DataPipelineWriter {
    void run(List<Portfolio> portfolioList);
}
