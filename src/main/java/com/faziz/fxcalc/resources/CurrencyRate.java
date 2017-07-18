package com.faziz.fxcalc.resources;

public class CurrencyRate {
    
    private final String currency;
    private final String rate;

    public CurrencyRate(String currency, String rate) {
        this.currency = currency;
        this.rate = rate;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }
}
