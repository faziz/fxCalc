package com.faziz.fxcalc.resources;

public class DirectRate {
    
    private final String currencyMapping;
    private final Double rate;

    public DirectRate(String currencyMapping, Double rate) {
        this.currencyMapping = currencyMapping;
        this.rate = rate;
    }

    /**
     * @return the currencyMapping
     */
    public String getCurrencyMapping() {
        return currencyMapping;
    }

    /**
     * @return the rate
     */
    public Double getRate() {
        return rate;
    }
}
