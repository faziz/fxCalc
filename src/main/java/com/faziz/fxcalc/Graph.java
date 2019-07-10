package com.faziz.fxcalc;

import static java.lang.String.format;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {

    private final Map<Vertex, LinkedList<FxRate>> adjacentVerticesList = new HashMap<>();
    private final Map<String, Double> ratesCache = new HashMap<>();

    public Vertex addVertex(Vertex vertex) {
        this.adjacentVerticesList.put(vertex, new LinkedList<>());
        return vertex;
    }

    public void addEdge(Vertex v1, Vertex v2, Double fxRate) {
        adjacentVerticesList.get(v1).addFirst(new FxRate(v1, v2, fxRate));
    }

    public void addEdge(Vertex v1, Vertex v2, Vertex crossRateCurrency) {
        adjacentVerticesList.get(v1).addFirst(new FxRate(v1, v2, crossRateCurrency));
    }

    public Double getRate(Vertex v1, Vertex v2)
            throws IllegalArgumentException {

        String key = calculateKey(v1, v2);
        if (ratesCache.containsKey(key)) {
            return ratesCache.get(key);
        }

        checkArugment(adjacentVerticesList.containsKey(v1), "Base currency doesn't exist.");
        LinkedList<FxRate> adjList = adjacentVerticesList.get(v1);

        Double rate = null;
        for (FxRate edge : adjList) {
            if (edge.getTargetCurrency().equals(v2)) {
                if (edge.getRate().isPresent()) {
                    return edge.getRate().get();
                } else {
                    Vertex crossRateCurrency = edge.getCrossRateCurrency().get();
                    if (!v2.equals(crossRateCurrency)) {
                        Double fxRate1 = getRate(v1, crossRateCurrency);
                        Double fxRate2 = getRate(crossRateCurrency, v2);
                        rate = ratesFound(fxRate1, fxRate2) ? fxRate1 * fxRate2 : null;
                    }
                }
            }
        }

        ratesCache.putIfAbsent(key, rate);
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

    private String calculateKey(Vertex v1, Vertex v2) {
        return format("%s-%s", v1.getCurrency(), v2.getCurrency());
    }
}
