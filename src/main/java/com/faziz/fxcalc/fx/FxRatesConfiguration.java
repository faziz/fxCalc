package com.faziz.fxcalc.fx;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * This bean sets up various rates used in the calculation.
 * @author faisal
 */
@Component
public class FxRatesConfiguration {
    
    /** Sheet name containing the rate matrix. */
    private final String CURRENCY_RATES = "/rate_matrix.xlsx";
    /** Sheet name containing direct rates for currency pair. */
    private final String DIRECT_RATES_SHEET = "direct_rates";
    /** Declares how the conversion between two currencies is to take place. */
    private final String RATES_MATRIX_SHEET = "rate_matrix";
    /** Contains rates for currency pairs. */
    private Map<String, BigDecimal> directRates = new HashMap<>();
    /** Provides inter-currency exchange rate calculation method. */
    private Map<String, Map<String, String>> ratesMatrix = new HashMap<>();
    
    @PostConstruct
    public void initialize() {
        Workbook wb = loadRatesFile(CURRENCY_RATES);
        assert null != wb;
        
        loadDirectRates(wb);
        loadRatesMatrix(wb);
    }
    
    @Bean(name = "directRates")
    public Map<String, BigDecimal> getDirectRates() {
        return new HashMap<>(directRates);
    }
    
    @Bean(name = "ratesMatrix")
    public Map<String, Map<String, String>> getRatesMatrix() {
        return new HashMap<>(ratesMatrix);
    }
    
    /**
     * Load Excel file containing the direct rates and rates matrix.
     * @param filename
     * @return 
     * @throws RuntimeException if the file is not found or if file was not MS-Excel.
     */
    private Workbook loadRatesFile(String filename) throws RuntimeException {
        
        try (InputStream stream = Files.newInputStream(
                Paths.get(this.getClass().getResource(filename).toURI()))) {
            
            return WorkbookFactory.create(stream);
            
        } catch (IOException | InvalidFormatException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Loads direct rates.
     * @param wb 
     */
    private void loadDirectRates(Workbook wb) {
        Sheet sheet = wb.getSheet(DIRECT_RATES_SHEET);
        assert null != sheet;
        
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell pairedRateName = row.getCell(0);
            Cell pairRateCell = row.getCell(1);
            
            if( null != pairedRateName && null != pairRateCell ) {
                String pairName = row.getCell(0).getStringCellValue();
                BigDecimal pairRate = BigDecimal.valueOf(pairRateCell.getNumericCellValue());
                directRates.put(pairName.toUpperCase(), pairRate);
            }
        }
    }
    
    /**
     * Loads rates matrix.
     * @param wb 
     */
    private void loadRatesMatrix(Workbook wb) {
        Sheet sheet = wb.getSheet(RATES_MATRIX_SHEET);
        assert null != sheet;
        
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            
            Row row = sheet.getRow(i);
            String curr = row.getCell(0).getStringCellValue();
            
            if( !ratesMatrix.containsKey(curr) ){
                ratesMatrix.put(curr, new HashMap<>());
            }
            
            Map<String, String> matrix = new HashMap<>();
            for (int j = 1; j < row.getLastCellNum(); j++) {
                String rate = row.getCell(j).getStringCellValue().trim().toUpperCase();
                final String curr2 = sheet.getRow(0).getCell(j).getStringCellValue();
                
                matrix.put(curr2, rate);
            }
            ratesMatrix.put(curr, matrix);
        }
    }
}
