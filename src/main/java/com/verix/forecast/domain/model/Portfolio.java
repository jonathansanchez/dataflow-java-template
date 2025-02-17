package com.verix.forecast.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Portfolio implements Serializable {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final String PERCENT = "100";
    private static final int ALLOWED_DECIMALS = 2;

    private final String country;
    private final LocalDate date;
    private final Integer total;
    private final Integer expired;
    private final BigDecimal expiredKri;
    private final Integer expiring;
    private final BigDecimal expiringKri;
    private final LocalDate createdAt;

    public Portfolio(String country, LocalDate date, Integer total, Integer expired, Integer expiring) {
        this.country     = country;
        this.date        = LocalDate.parse(date.format(FORMAT));
        this.total       = total;
        this.expired     = expired;
        this.expiring    = expiring;
        this.expiredKri  = calculateKri(expired, total);
        this.expiringKri = calculateKri(expiring, total);
        this.createdAt   = setCreatedAt();
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

    @Override
    public String toString() {
        return "Portfolio{" +
                "country='" + country + '\'' +
                ", date=" + date +
                ", total=" + total +
                ", expired=" + expired +
                ", expiredKri=" + expiredKri +
                ", expiring=" + expiring +
                ", expiringKri=" + expiringKri +
                '}';
    }
}
