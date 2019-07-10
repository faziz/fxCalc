package com.faziz.fxcalc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class GraphTest {

    private static final Graph graph = new Graph();
    private static final Vertex SGD = new Vertex("SGD");
    private static final Vertex EUR = new Vertex("EUR");
    private static final Vertex CHF = new Vertex("CHF");
    private static final Vertex JPY = new Vertex("JPY");
    private static final Vertex USD = new Vertex("USD");
    private static final Vertex RMB = new Vertex("RMB");
    private static final Vertex INR = new Vertex("INR");

    @BeforeClass
    public static void before() {
        graph.addVertex(SGD);
        graph.addVertex(EUR);
        graph.addVertex(CHF);
        graph.addVertex(JPY);
        graph.addVertex(USD);
        graph.addVertex(RMB);
        graph.addVertex(INR);

        //SGD Rates
        graph.addEdge(SGD, SGD, 1.00);
        graph.addEdge(SGD, USD, 0.74);
        graph.addEdge(SGD, EUR, USD);
        graph.addEdge(SGD, INR, JPY);
        graph.addEdge(SGD, RMB, JPY);
        graph.addEdge(SGD, CHF, EUR);

        //Euro Rates
        graph.addEdge(EUR, CHF, 1.12);
        graph.addEdge(EUR, JPY, 124.00);
        graph.addEdge(EUR, EUR, 1.00);
        graph.addEdge(EUR, USD, 1.13);
        graph.addEdge(EUR, SGD, USD);
        graph.addEdge(EUR, INR, USD);
        graph.addEdge(EUR, RMB, USD);

        //USD Rates
        graph.addEdge(USD, USD, 1.00);
        graph.addEdge(USD, EUR, 0.89);
        graph.addEdge(USD, SGD, 1.35);
        graph.addEdge(USD, JPY, 110.00);
        graph.addEdge(USD, INR, 69.17);
        graph.addEdge(USD, RMB, 6.72);
        graph.addEdge(USD, CHF, EUR);

        //JPY Rates
        graph.addEdge(JPY, USD, 0.01);
        graph.addEdge(JPY, EUR, USD);
        graph.addEdge(JPY, SGD, USD);
        graph.addEdge(JPY, JPY, 1.00);
        graph.addEdge(JPY, INR, USD);
        graph.addEdge(JPY, RMB, USD);
        graph.addEdge(JPY, CHF, EUR);

        //INR Rates
        graph.addEdge(INR, USD, 0.01);
        graph.addEdge(INR, EUR, 0.01);
        graph.addEdge(INR, SGD, 0.02);
        graph.addEdge(INR, JPY, 1.59);
        graph.addEdge(INR, INR, 1.00);
        graph.addEdge(INR, RMB, USD);
        graph.addEdge(INR, CHF, EUR);

        //RMB Rates
        graph.addEdge(RMB, USD, 0.15);
        graph.addEdge(RMB, EUR, USD);
        graph.addEdge(RMB, SGD, USD);
        graph.addEdge(RMB, JPY, USD);
        graph.addEdge(RMB, INR, USD);
        graph.addEdge(RMB, RMB, 1.00);
        graph.addEdge(RMB, CHF, EUR);

        //CHF Rates
        graph.addEdge(CHF, USD, 1.01);
        graph.addEdge(CHF, EUR, USD);
        graph.addEdge(CHF, SGD, USD);
        graph.addEdge(CHF, JPY, JPY);
        graph.addEdge(CHF, INR, JPY);
        graph.addEdge(CHF, RMB, JPY);
        graph.addEdge(CHF, CHF, 1.00);
    }

    @Test
    public void testUSDtoEURO() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.89), result);
    }

    @Test
    public void testUSDtoUSD() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.00), result);
    }

    @Test
    public void testUSDtoSGD() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = SGD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.35), result);
    }

    @Test
    public void testUSDtoJPY() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(110.00), result);
    }

    @Test
    public void testUSDtoINR() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(69.17), result);
    }

    @Test
    public void testUSDtoRMB() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(6.72), result);
    }

    @Test
    public void testUSDtoCHF() {
        Vertex baseCurrency = USD;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.9968), fmt(result));
    }

    @Test
    public void testEuroToUSD() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.13), fmt(result));
    }

    @Test
    public void testEuroToEuro() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.00), fmt(result));
    }

    @Test
    public void testEuroToSGD() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = SGD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.5255), fmt(result));
    }

    @Test
    public void testEuroToJPY() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(124.0), result);
    }

    @Test
    public void testEuroToINR() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(78.1621), fmt(result));
    }

    @Test
    public void testEuroToRMB() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(7.5936), fmt(result));
    }

    @Test
    public void testEuroToCHF() {
        Vertex baseCurrency = EUR;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.12), result);
    }

    @Test
    public void testSGDToUSD() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.74), result);
    }

    @Test
    public void testSGDToEuro() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.6586), result);
    }

    @Test
    public void testSGDToJPY() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }

    @Test
    public void testSGDToINR() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }

    @Test
    public void testSGDToRMB() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }

    @Test
    public void testSGDToCHF() {
        Vertex baseCurrency = SGD;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.7376), fmt(result));
    }

    @Test
    public void testJPYtoUSD() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.01), result);
    }

    @Test
    public void testJPYtoEUR() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.0089), result);
    }

    @Test
    public void testJPYtoSGD() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = SGD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.0135), fmt(result));
    }

    @Test
    public void testJPYtoJPY() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1), result);
    }

    @Test
    public void testJPYtoINR() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.6917), fmt(result));
    }

    @Test
    public void testJPYtoRMB() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.0672), fmt(result));
    }

    @Test
    public void testJPYtoCHF() {
        Vertex baseCurrency = JPY;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.009968), fmt(result, 6));
    }

    @Test
    public void testRMBtoUSD() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.15), result);
    }

    @Test
    public void testRMBtoEUR() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.1335), fmt(result));
    }

    @Test
    public void testRMBtoSGD() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = SGD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.2025), fmt(result));
    }

    @Test
    public void testRMBtoJPY() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(16.5), result);
    }

    @Test
    public void testRMBtoINR() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(10.3755), fmt(result));
    }

    @Test
    public void testRMBtoRMB() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1), result);
    }

    @Test
    public void testRMBtoCHF() {
        Vertex baseCurrency = RMB;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.14952), fmt(result, 5));
    }

    @Test
    public void testCHFtoUSD() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = USD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.01), result);
    }

    @Test
    public void testCHFtoEUR() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = EUR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(0.8989), result);
    }

    @Test
    public void testCHFtoSGD() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = SGD;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1.3635), fmt(result));
    }

    @Test
    public void testCHFtoJPY() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = JPY;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }
    
    @Test
    public void testCHFtoINR() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = INR;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }
    
    @Test
    public void testCHFtoRMB() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = RMB;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertNull(result);
    }
    
    @Test
    public void testCHFtoCHF() {
        Vertex baseCurrency = CHF;
        Vertex trgtCurrency = CHF;

        Double result = graph.getRate(baseCurrency, trgtCurrency);
        assertEquals(Double.valueOf(1), result);
    }

    private Double fmt(Double value) {
        return fmt(value, 4);
    }

    private Double fmt(Double value, Integer precision) {
        return Double.valueOf(formatter(precision).format(value));
    }

    private NumberFormat formatter(Integer precision) {
        NumberFormat fmt = new DecimalFormat();
        fmt.setMaximumFractionDigits(precision);
        return fmt;
    }
}
