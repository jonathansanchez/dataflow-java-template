package com.verix.forecast.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Portfolio implements Serializable {
    private static final DateTimeFormatter FORMAT           = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final String            PERCENT          = "100";
    private static final Integer           ALLOWED_DECIMALS = 2;

    private final String     country;
    private       String     strategy;
    private final LocalDate  date;
    private final Integer    total;
    private       Integer    forecastTotal;
    private final Integer    expired;
    private final BigDecimal expiredKri;
    private       Integer    forecastExpired;
    private       BigDecimal forecastExpiredKri;
    private final Integer    expiring;
    private final BigDecimal expiringKri;
    private       Integer    forecastExpiring;
    private       BigDecimal forecastExpiringKri;
    private       Integer    added;
    private       Integer    updated;
    private       Integer    removed;
    private       Integer    remediated;
    private final LocalDate  createdAt;

    public Portfolio(String country, LocalDate date, Integer total, Integer expired, Integer expiring) {
        this.country             = country;
        this.date                = LocalDate.parse(date.format(FORMAT));
        this.total               = total;
        this.forecastTotal       = total;
        this.expired             = expired;
        this.forecastExpired     = 0;
        this.expiring            = expiring;
        this.forecastExpiring    = expiring;
        this.forecastExpiringKri = calculateKri(expiring, total);;
        this.expiredKri          = calculateKri(expired, total);
        this.expiringKri         = calculateKri(expiring, total);
        this.createdAt           = setCreatedAt();
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    private BigDecimal calculateKri(Integer components, Integer total) {
        BigDecimal numerator   = new BigDecimal(components);
        BigDecimal denominator = new BigDecimal(total);

        return numerator
                .multiply(new BigDecimal(PERCENT))
                .divide(denominator, ALLOWED_DECIMALS, RoundingMode.HALF_UP);
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getExpiring() {
        return expiring;
    }

    public BigDecimal getExpiredKri() {
        return expiredKri;
    }

    public Integer getExpired() {
        return expired;
    }

    public BigDecimal getExpiringKri() {
        return expiringKri;
    }

    private LocalDate setCreatedAt() {
        LocalDate current = LocalDate.now();
        return LocalDate.parse(current.format(FORMAT));
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }

    public void calculateTotalRemediated() {
        this.remediated         = this.updated + this.removed;
        this.forecastExpired    = this.expired - this.remediated;
        this.forecastExpiredKri = calculateKri(this.forecastExpired, this.forecastTotal);
    }

    public void calculateAccumulatedRemediated(Integer previousRemediated) {
        this.remediated         = this.remediated + previousRemediated;
        this.forecastExpired    = this.forecastExpired - this.remediated;
        this.forecastExpiredKri = calculateKri(this.forecastExpired, this.forecastTotal);
    }

    public Integer getRemediated() {
        return remediated;
    }

    public Integer getAdded() {
        return added;
    }

    public Integer getUpdated() {
        return updated;
    }

    public Integer getRemoved() {
        return removed;
    }

    public void calculateTotalAdd(Integer add) {
        this.forecastTotal = this.forecastTotal + add;
    }

    public void calculateTotalRemove(Integer remove) {
        this.forecastTotal = this.forecastTotal - remove;
    }

    public void setForecastTotal(Integer forecastTotal) {
        this.forecastTotal = forecastTotal;
    }

    public Integer getForecastTotal() {
        return forecastTotal;
    }

    public Integer getForecastExpired() {
        return forecastExpired;
    }

    public BigDecimal getForecastExpiredKri() {
        return forecastExpiredKri;
    }

    public Integer getForecastExpiring() {
        return forecastExpiring;
    }

    public BigDecimal getForecastExpiringKri() {
        return forecastExpiringKri;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "country='" + country + '\'' +
                ", date=" + date.toString() +
                ", total=" + total +
                ", forecastTotal=" + forecastTotal +
                ", expired=" + expired +
                ", expiredKri=" + expiredKri +
                ", forecastExpired=" + forecastExpired +
                ", forecastExpiredKri=" + forecastExpiredKri +
                ", expiring=" + expiring +
                ", expiringKri=" + expiringKri +
                ", forecastExpiring=" + forecastExpiring +
                ", forecastExpiringKri=" + forecastExpiringKri +
                ", added=" + added +
                ", updated=" + updated +
                ", removed=" + removed +
                ", remediated=" + remediated +
                ", createdAt=" + createdAt +
                '}';
    }
}
