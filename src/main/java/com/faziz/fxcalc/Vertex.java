package com.faziz.fxcalc;

import java.util.Objects;

public class Vertex {

    private final String currency;
    private final Boolean visited;

    public Vertex(String currency) {
        this(currency, false);
    }
    
    private Vertex(String currency, Boolean visited) {
        this.currency = currency;
        this.visited = visited;
    }
    
    public Vertex visited() {
        return new Vertex(currency, true);
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public Boolean getVisited() {
        return visited;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.currency);
        hash = 11 * hash + Objects.hashCode(this.visited);
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
        final Vertex other = (Vertex) obj;
        return Objects.equals(this.currency, other.currency) && 
                Objects.equals(this.visited, other.visited);
    }

    @Override
    public String toString() {
        return "Vertex{" + "currency=" + currency + ", visited=" + visited + '}';
    }
}
