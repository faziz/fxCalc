package com.faziz.fxcalc.resources;

import java.util.ArrayList;
import java.util.List;

public class RateMatrix {
    
    private final String currency;
    private final List<CurrencyRate> currencyRates;
    
    public RateMatrix(String currency) {
        this.currency = currency;
        this.currencyRates = new ArrayList<>();
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the currencyRates
     */
    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }    
}
