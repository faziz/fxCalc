package com.faziz.fxcalc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Map<Vertex, LinkedList<FxRate>> adjacentVerticesList = new HashMap<>();
    private final Set<Vertex> visited = new HashSet<>();

    public Vertex addVertex(Vertex vertex) {
        this.adjacentVerticesList.put(vertex, new LinkedList<>());
        return vertex;
    }

    public void addEdge(Vertex baseCurrency, Vertex targetCurrency, Double fxRate) {
        adjacentVerticesList.get(baseCurrency).addFirst(new FxRate(baseCurrency, targetCurrency, fxRate));
    }

    public void addEdge(Vertex baseCurrency, Vertex targetCurrency, Vertex crossRateCurrency) {
        adjacentVerticesList.get(baseCurrency).addFirst(new FxRate(baseCurrency, targetCurrency, crossRateCurrency));
    }

    public Double getRate(Vertex baseCurrency, Vertex targetCurrency)
            throws IllegalArgumentException {

        checkArugment(adjacentVerticesList.containsKey(baseCurrency), "Base currency doesn't exist.");
        //Handle cyclical dependencies.
        if (visited.contains(baseCurrency)) {
            //Already visited. Handling circular dependency.
            return null;
        }

        Double rate = null;
        for (FxRate edge : adjacentVerticesList.get(baseCurrency)) {
            if (edge.getTargetCurrency().equals(targetCurrency)) {
                if (edge.getRate().isPresent()) {
                    visited.add(baseCurrency);
                    return edge.getRate().get();
                } else {
                    Vertex crossRateCurrency = edge.getCrossRateCurrency().get();
                    if (!targetCurrency.equals(crossRateCurrency)) {
                        Double fxRate1 = getRate(baseCurrency, crossRateCurrency);
                        Double fxRate2 = getRate(crossRateCurrency, targetCurrency);
                        rate = ratesFound(fxRate1, fxRate2) ? fxRate1 * fxRate2 : null;
                    }
                }
            }
        }
        return rate;
    }

    private static boolean ratesFound(Double fxRate1, Double fxRate2) {
        return null != fxRate1 && null != fxRate2;
    }

    @Override
    public String toString() {
        return "adjacentVerticesList=" + adjacentVerticesList + '}';
    }

    private void checkArugment(boolean condition, String message)
            throws IllegalArgumentException {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
