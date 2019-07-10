package com.faziz.fxcalc;

import java.math.BigDecimal;
import java.util.Objects;

public class ConversionInput {
    
    private final String baseCurrency;
    private final String targetCurrency;
    private final BigDecimal fromAmount;

    public ConversionInput(String baseCurrency, String targetCurrency, BigDecimal fromAmount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.fromAmount = fromAmount;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.targetCurrency);
        hash = 41 * hash + Objects.hashCode(this.fromAmount);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConversionInput other = (ConversionInput) obj;
        if (!Objects.equals(this.baseCurrency, other.baseCurrency)) {
            return false;
        }
        if (!Objects.equals(this.targetCurrency, other.targetCurrency)) {
            return false;
        }
        if (!Objects.equals(this.fromAmount, other.fromAmount)) {
            return false;
        }
        return true;
    }
}
