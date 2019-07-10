package com.faziz.fxcalc;

import java.util.Objects;

public class Vertex {

    private final String currency;

    public Vertex(String currency) {
        this.currency = currency;
    }
    
    public String getCurrency() {
        return currency;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.currency);
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
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "currency=" + currency + '}';
    }
}
