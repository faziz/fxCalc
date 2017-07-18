package com.faziz.fxcalc.fx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests all conversion scenarios.
 * @author faisal
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FxCalculatorTest {
    
    @Resource(type = FxCalculator.class)
    private FxCalculator fxCalc;
    
    @Resource(type = FxRatesConfiguration.class)
    private FxRatesConfiguration ratesConfig;
    
    /**
     * Test of getDirectRates method, of class FxCalculator.
     */
    @Test
    public void testInitialization() {
        Map<String, BigDecimal> directRates = ratesConfig.getDirectRates();
        
        assertNotNull(directRates);
        assertEquals( 10, directRates.size() );
        assertTrue( directRates.containsKey("EURUSD") );
        assertEquals( BigDecimal.valueOf(1.2315d), directRates.get("EURUSD") );
        
        Map<String, Map<String, String>> ratesMatrix = ratesConfig.getRatesMatrix();
        assertNotNull(ratesMatrix);
        assertEquals(11, ratesMatrix.size());
        assertTrue(ratesMatrix.containsKey("DKK"));
        
        Map<String, String> matrix = ratesMatrix.get("EUR");
        assertEquals(11, matrix.size());
        assertEquals("D", matrix.get("DKK"));
        assertEquals("D", matrix.get("NOK"));
        assertEquals("USD", matrix.get("NZD"));
        
        matrix = ratesMatrix.get("CNY");
        assertEquals(11, matrix.size());
        assertEquals("USD", matrix.get("AUD"));
        assertEquals("USD", matrix.get("JPY"));
        assertEquals("D", matrix.get("USD"));
    }
    
    @Test
    public void testCalcFxDirectRate(){
        Result result = fxCalc.calc("AUD 100.00 in USD");
        assertNotNull(result);
        assertTrue(result.isSuccessful());        
        assertEquals(BigDecimal.valueOf(83.71d).setScale(2), result.getCovertedAmount().setScale(2));
        
        result = fxCalc.calc("AUD 100.00 in AUD");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        assertEquals(BigDecimal.valueOf(100.00d).setScale(2), result.getCovertedAmount().setScale(2));
        
        result = fxCalc.calc("CAD 50.00 in USD");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        assertEquals(BigDecimal.valueOf(43.55d), 
            result.getCovertedAmount().setScale(2, RoundingMode.HALF_DOWN));
    }
    
    @Test
    public void testCalcFxInverseRate() {
        Result result = fxCalc.calc("JPY 100.00 in USD");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        assertEquals(BigDecimal.valueOf(0.83d), 
            result.getCovertedAmount().setScale(2, RoundingMode.HALF_DOWN));
    }
    
    @Test
    public void testCalcFxCrossRate() {
        
        Result result = fxCalc.calc("AUD 1.00 in JPY");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        assertEquals(BigDecimal.valueOf(100.41d), 
            result.getCovertedAmount().setScale(2, RoundingMode.HALF_DOWN));
    }
    
    @Test
    public void testCalcFxInversedCrossRate() {

        Result result = fxCalc.calc("CAD 50.00 in NZD");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        assertEquals(BigDecimal.valueOf(56.20d).setScale(2, RoundingMode.HALF_DOWN), 
            result.getCovertedAmount().setScale(2, RoundingMode.HALF_DOWN));
    }
}
