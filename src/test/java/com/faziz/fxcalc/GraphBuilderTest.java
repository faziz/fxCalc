package com.faziz.fxcalc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import static java.lang.String.format;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class GraphBuilderTest {
    
    private static Reader reader;
    private static Graph graph;
    private static List<FxRate> fxRatesData;
    private final FxRate fxRate;
    
    @Parameters
    public static List<FxRate> fxRatesResultData() {
        fxRatesData = new ArrayList<>();
        Vertex usd = new Vertex("USD");
        Vertex eur = new Vertex("EUR");
        Vertex sgd = new Vertex("SGD");
        Vertex jpy = new Vertex("JPY");
        Vertex inr = new Vertex("INR");
        Vertex rmb = new Vertex("RMB");
        Vertex chf = new Vertex("CHF");
        
        fxRatesData.add(new FxRate(usd, eur, 0.89));
        fxRatesData.add(new FxRate(usd, usd, 1.00));
        fxRatesData.add(new FxRate(usd, sgd, 1.35));
        fxRatesData.add(new FxRate(usd, jpy, 110.00));
        fxRatesData.add(new FxRate(usd, inr, 69.17));
        fxRatesData.add(new FxRate(usd, rmb, 6.72));
        fxRatesData.add(new FxRate(usd, chf, 0.9968));
        
        fxRatesData.add(new FxRate(eur, usd, 1.13));
        fxRatesData.add(new FxRate(eur, eur, 1.00));
        fxRatesData.add(new FxRate(eur, sgd, 1.5255));
        fxRatesData.add(new FxRate(eur, jpy, 124.0));
        fxRatesData.add(new FxRate(eur, inr, 78.1621));
        fxRatesData.add(new FxRate(eur, rmb, 7.5936));
        fxRatesData.add(new FxRate(eur, chf, 1.12));

        fxRatesData.add(new FxRate(sgd, usd, 0.74));
        fxRatesData.add(new FxRate(sgd, eur, 0.6586));
        fxRatesData.add(new FxRate(sgd, sgd, 1.00));
        fxRatesData.add(new FxRate(sgd, jpy));
        fxRatesData.add(new FxRate(sgd, inr));
        fxRatesData.add(new FxRate(sgd, rmb));
        fxRatesData.add(new FxRate(sgd, chf, eur, 0.7376));
        
        fxRatesData.add(new FxRate(jpy, usd, 0.01));
        fxRatesData.add(new FxRate(jpy, eur, usd, 0.0089));
        fxRatesData.add(new FxRate(jpy, sgd, usd, 0.0135));
        fxRatesData.add(new FxRate(jpy, jpy, 1.0));
        fxRatesData.add(new FxRate(jpy, inr, 0.6917));
        fxRatesData.add(new FxRate(jpy, rmb, 0.0672));
        fxRatesData.add(new FxRate(jpy, chf, 0.009968));

        fxRatesData.add(new FxRate(inr, usd, 0.01));
        fxRatesData.add(new FxRate(inr, eur, 0.01));
        fxRatesData.add(new FxRate(inr, sgd, 0.02));
        fxRatesData.add(new FxRate(inr, jpy, 1.59));
        fxRatesData.add(new FxRate(inr, inr, 1.00));
        fxRatesData.add(new FxRate(inr, rmb, usd, 0.0672));
        fxRatesData.add(new FxRate(inr, chf, eur, 0.0112));
        
        fxRatesData.add(new FxRate(rmb, usd, 0.15));
        fxRatesData.add(new FxRate(rmb, eur, usd, 0.1335));
        fxRatesData.add(new FxRate(rmb, sgd, usd, 0.2025));
        fxRatesData.add(new FxRate(rmb, jpy, usd, 16.5));
        fxRatesData.add(new FxRate(rmb, inr, usd, 10.3755));
        fxRatesData.add(new FxRate(rmb, rmb, 1d));
        fxRatesData.add(new FxRate(rmb, chf, eur, 0.14952));

        fxRatesData.add(new FxRate(chf, usd, 1.01));
        fxRatesData.add(new FxRate(chf, eur, usd, 0.8989));
        fxRatesData.add(new FxRate(chf, sgd, usd, 1.3635));
        fxRatesData.add(new FxRate(chf, jpy, jpy));
        fxRatesData.add(new FxRate(chf, inr, jpy));
        fxRatesData.add(new FxRate(chf, rmb, jpy));
        fxRatesData.add(new FxRate(chf, chf, 1.00));

        return fxRatesData;
    }
    
    @Before
    public void setUp() throws FileNotFoundException, IOException {
        reader = new InputStreamReader(GraphBuilderTest.class.getResourceAsStream("/ref.csv"));
        graph = GraphBuilder.build(reader);
    }
    
    public GraphBuilderTest(FxRate fxRate) {
        this.fxRate = fxRate;
    }
    
    @Test
    public void testGraphValues() {
        String msg = format("[%s-%s] ->", fxRate.getBaseCurrency().getCurrency(), 
            fxRate.getTargetCurrency().getCurrency());
        Double rate = graph.getRate(fxRate.getBaseCurrency(), fxRate.getTargetCurrency());

        if (fxRate.getRate().isPresent()) {
            Double expected = fmt(fxRate.getRate().get());
            Double result   = fmt(rate);
            assertEquals(msg, expected, result);
        } else {
            assertNull(msg, rate);
        }
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
