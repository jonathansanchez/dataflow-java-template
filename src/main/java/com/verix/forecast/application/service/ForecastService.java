package com.verix.forecast.application.service;

import com.verix.forecast.domain.model.Component;
import com.verix.forecast.domain.model.DataPipelineWriter;
import com.verix.forecast.domain.model.Portfolio;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ForecastService {

    private static final int YEARS_TO_ADD  = 2;
    private static final int MONTHS_TO_ADD = 1;

    private final DataPipelineWriter pipeline;

    public ForecastService(DataPipelineWriter pipeline) {
        this.pipeline = pipeline;
    }

    public List<Portfolio> execute(List<Component> componentList) {
        ArrayList<LocalDate> dates           = getPortfolioDates();
        String               country         = getCountry(componentList);
        Integer              totalComponents = componentList.size();
        ArrayList<Portfolio> portfolios      = new ArrayList<>();

        dates.forEach(portfolioDate -> {
            Integer expired  = countExpiredComponentsByDate(componentList, portfolioDate);
            Integer expiring = countExpiringComponentsByDate(componentList, portfolioDate);
            portfolios.add(new Portfolio(country, portfolioDate, totalComponents, expired, expiring));
        });

        portfolios.forEach(System.out::println);

        pipeline.run(portfolios);

        return portfolios;
    }

    private ArrayList<LocalDate> getPortfolioDates() {
        LocalDate            currentDate = LocalDate.now();
        LocalDate            endDate     = currentDate.plusYears(YEARS_TO_ADD);
        ArrayList<LocalDate> dates       = new ArrayList<>();

        Stream.iterate(currentDate, date -> date.isBefore(endDate), date -> date.plusMonths(MONTHS_TO_ADD))
                .map(date -> date.with(TemporalAdjusters.lastDayOfMonth()))
                .forEach(dates::add);
        return dates;
    }

    private String getCountry(List<Component> componentList) {
        return componentList
                .stream()
                .findFirst()
                .get()
                .getCountry();
    }

    private Integer countExpiringComponentsByDate(List<Component> componentList, LocalDate portfolioDate) {
        long expiring = componentList
                .stream()
                .filter(Component::hasEol)
                .filter(component -> component.eolIsBetweenTwoYears(portfolioDate))
                .count();
        return Math.toIntExact(expiring);
    }

    private Integer countExpiredComponentsByDate(List<Component> componentList, LocalDate portfolioDate) {
        long expired = componentList
                .stream()
                .filter(Component::hasEol)
                .filter(component -> component.eolIsExpired(portfolioDate))
                .count();
        return Math.toIntExact(expired);
    }
}
