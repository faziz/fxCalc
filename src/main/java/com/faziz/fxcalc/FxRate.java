package com.faziz.fxcalc;

import java.util.Optional;
import static java.util.Optional.*;

public class FxRate {

    private final Vertex baseCurrency;
    private final Vertex targetCurrency;
    private final Optional<Double> rate;
    private final Optional<Vertex> crossRateCurrency;

    public FxRate(Vertex baseCurrency, Vertex targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.crossRateCurrency = empty();
        this.rate = empty();
    }
    

    public FxRate(Vertex baseCurrency, Vertex targetCurrency, Double rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = of(rate);
        this.crossRateCurrency = empty();
    }

    public FxRate(Vertex baseCurrency, Vertex targetCurrency, Vertex crossRateCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.crossRateCurrency = of(crossRateCurrency);
        this.rate = empty();
    }
    
    public FxRate(Vertex baseCurrency, Vertex targetCurrency, Vertex crossRateCurrency, Double rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.crossRateCurrency = of(crossRateCurrency);
        this.rate = ofNullable(rate);
    }

    public Vertex getBaseCurrency() {
        return baseCurrency;
    }

    public Vertex getTargetCurrency() {
        return targetCurrency;
    }

    public Optional<Double> getRate() {
        return rate;
    }

    public Optional<Vertex> getCrossRateCurrency() {
        return crossRateCurrency;
    }

    @Override
    public String toString() {
        return "Edge{" + "baseCurrency=" + baseCurrency + ", targetCurrency=" + targetCurrency + ", rate=" + rate + ", crossRateCurrency=" + crossRateCurrency + '}';
    }
}
