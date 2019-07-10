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

    public void addEdge(Vertex v1, Vertex v2, Double fxRate) {
        adjacentVerticesList.get(v1).addFirst(new FxRate(v1, v2, fxRate));
    }

    public void addEdge(Vertex v1, Vertex v2, Vertex crossRateCurrency) {
        adjacentVerticesList.get(v1).addFirst(new FxRate(v1, v2, crossRateCurrency));
    }

    public Double getRate(Vertex v1, Vertex v2)
            throws IllegalArgumentException {

        checkArugment(adjacentVerticesList.containsKey(v1), "Base currency doesn't exist.");
        LinkedList<FxRate> adjList = adjacentVerticesList.get(v1);

        //Handle cyclical dependencies.
        if (visited.contains(v1)) {
            //Already visited. Returning 1 means there wont be any change to the result when multiplied.
            return 1.00;
        }

        Double rate = null;
        for (FxRate edge : adjList) {
            if (edge.getTargetCurrency().equals(v2)) {
                if (edge.getRate().isPresent()) {
                    visited.add(v1);
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
