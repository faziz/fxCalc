package com.faziz.fxcalc;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class CurrencyConversionReaderTest {
    
    /**
     * Test of read method, of class CurrencyConversionInputReader.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testReadCurrencyToCurrencyConversionData() throws FileNotFoundException {
        List<ConversionInput> data = new CurrencyConversionInputReader().read(reader());
        assertNotNull(data);
        
        assertEquals(new ConversionInput("USD", "JPY", BigDecimal.valueOf(10.00)), data.get(0));
        assertEquals(new ConversionInput("CHF", "EUR", BigDecimal.valueOf(10.00)), data.get(1));
        assertEquals(new ConversionInput("CHF", "INR", BigDecimal.valueOf(20.00)), data.get(2));
        assertEquals(new ConversionInput("INR", "SGD", BigDecimal.valueOf(1000.00)), data.get(3));
        assertEquals(new ConversionInput("SGD", "JPY", BigDecimal.valueOf(100.00)), data.get(4));
        assertEquals(new ConversionInput("RMB", "EUR", BigDecimal.valueOf(10000.00)), data.get(5));
    }
    
    private Reader reader() {
        return new InputStreamReader(CurrencyConversionReaderTest.class.getResourceAsStream("/data.csv"));
    }
}
