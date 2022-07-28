package com.ninjaone.backendinterviewproject.domain.custom;

public interface MonthlyCoastReport {
    Long getClientId();
    String getClientName();
    Integer getPeriod();
    Double getMonthlyCoast();
}
