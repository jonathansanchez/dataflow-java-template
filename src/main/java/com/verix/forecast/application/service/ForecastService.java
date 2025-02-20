package com.verix.forecast.application.service;

import com.verix.forecast.application.service.request.ForecastRequest;
import com.verix.forecast.domain.model.ActionType;
import com.verix.forecast.domain.model.DataPipelineForecast;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.domain.model.Remediation;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.toIntExact;

public class ForecastService {

    private static final int MONTHS_TO_ADD   = 1;
    private static final int MONTHS_TO_MINUS = 1;

    private final DataPipelineForecast pipeline;

    public ForecastService(DataPipelineForecast pipeline) {
        this.pipeline = pipeline;
    }

    public List<Portfolio> execute(ForecastRequest request) {
        List<Portfolio>   portfolioList   = request.getPortfolioList();
        List<Remediation> remediationList = request.getRemediationList();
        String            strategy        = remediationList.stream().findFirst().get().getStrategy();

        portfolioList.forEach(portfolio -> {
            portfolio.setStrategy(strategy);

            Integer totalAdd = countComponentsByActionAndDate(remediationList, ActionType.ADD, portfolio);
            portfolio.setAdded(totalAdd);

            Integer totalUpdate = countComponentsByActionAndDate(remediationList, ActionType.UPDATE, portfolio);
            portfolio.setUpdated(totalUpdate);

            Integer totalRemove = countComponentsByActionAndDate(remediationList, ActionType.REMOVE, portfolio);
            portfolio.setRemoved(totalRemove);

            portfolio.calculateTotalRemediated();

            //Update remediated accumulated from current month Portfolio
            LocalDate previousPortfolioDate = portfolio.getDate().minusMonths(MONTHS_TO_MINUS).with(TemporalAdjusters.lastDayOfMonth());
            Optional<Portfolio> optionalPreviousPortfolio = portfolioList
                    .stream()
                    .filter(p -> p.getDate().isEqual(previousPortfolioDate))
                    .findFirst();

            if (optionalPreviousPortfolio.isPresent()) {
                Portfolio previousPortfolio = optionalPreviousPortfolio.get();
                portfolio.calculateAccumulatedRemediated(previousPortfolio.getRemediated());
            }

            //Update universe from next month Portfolio
            LocalDate nextPortfolioDate = portfolio.getDate().plusMonths(MONTHS_TO_ADD).with(TemporalAdjusters.lastDayOfMonth());
            portfolioList
                    .stream()
                    .filter(p -> p.getDate().isEqual(nextPortfolioDate))
                    .findFirst()
                    .ifPresent(p -> {
                        p.setForecastTotal(portfolio.getForecastTotal());
                        updateTotalAdd(p, totalAdd);
                        updateTotalRemove(p, totalRemove);
                    });
        });

        portfolioList.forEach(System.out::println);

        //pipeline.run(portfolioList);

        return portfolioList;
    }

    private Integer countComponentsByActionAndDate(List<Remediation> remediationList, ActionType type, Portfolio portfolio) {
        long total = remediationList
                .stream()
                .filter(remediation -> isEqualToAction(remediation, type.name()))
                .filter(remediation -> isRemediationDateInPortfolioDate(portfolio, remediation))
                .count();
        return toIntExact(total);
    }

    private Boolean isEqualToAction(Remediation remediation, String action) {
        return Objects.equals(remediation.getAction().getValue(), action);
    }

    private Boolean isRemediationDateInPortfolioDate(Portfolio portfolio, Remediation remediation) {
        LocalDate remediationDate = LocalDate.parse(remediation.getDeliveryDate().getValue());
        return remediationDate.getYear() == portfolio.getDate().getYear() && remediationDate.getMonth() == portfolio.getDate().getMonth();
    }

    private void updateTotalAdd(Portfolio portfolio, long totalAdd) {
        if (totalAdd > 0) {
            portfolio.calculateTotalAdd(toIntExact(totalAdd));
        }
    }

    private void updateTotalRemove(Portfolio portfolio, long totalRemove) {
        if (totalRemove > 0) {
            portfolio.calculateTotalRemove(toIntExact(totalRemove));
        }
    }
}
