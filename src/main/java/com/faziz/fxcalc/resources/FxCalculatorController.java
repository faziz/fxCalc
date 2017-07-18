package com.faziz.fxcalc.resources;

import com.faziz.fxcalc.fx.FxCalculator;
import com.faziz.fxcalc.fx.Result;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes Fx Calculation.
 * @author faisal
 */
@RestController
@RequestMapping("/api/calc")
public class FxCalculatorController {
    
    /** Contains rates for currency pairs. */
    @Resource(name = "directRates")
    private Map<String, BigDecimal> directRates;
    /** Provides inter-currency exchange rate calculation method. */
    @Resource(name = "ratesMatrix")
    private Map<String, Map<String, String>> ratesMatrix;
    /** Calculation engine. **/
    @Resource(type = FxCalculator.class)
    private FxCalculator fxCalc;
    
    @RequestMapping("drates")
    public List<DirectRate> getDirectRates() {
        return directRates.entrySet().stream().map((entry) -> {
            //Create direct rate instance.
            return new DirectRate(
                entry.getKey(), entry.getValue().doubleValue());
        }).collect(Collectors.toList());
    }
    
    @RequestMapping("rmatrix")
    public List<RateMatrix> getRatesMatrix() {
        List<RateMatrix> rateMatrics = new ArrayList<>(ratesMatrix.size());

        ratesMatrix.entrySet().stream().map((entry) -> {
            
            String key = entry.getKey();
            Map<String, String> value = entry.getValue();
            RateMatrix rateMatric = new RateMatrix(key);

            value.entrySet().forEach((currencyRate) -> {
                String currency = currencyRate.getKey();
                String rate = currencyRate.getValue();

                rateMatric.getCurrencyRates().add(
                    new CurrencyRate(currency, rate));
            });
            return rateMatric;
        }).forEachOrdered((rateMatric) -> {
            rateMatrics.add(rateMatric);
        });

        return rateMatrics;
    }
    
    @RequestMapping("currencies")
    public List<String> getCurrencies(){
        return new ArrayList<>(ratesMatrix.keySet());
    }
    
    @RequestMapping
    public Result calculate(@Valid FxCalcForm form) {
        String ccy1 = form.getCcy1();
        String ccy2 = form.getCcy2();
        Double amount = form.getAmount();
        
        return fxCalc.calc(ccy1, ccy2, BigDecimal.valueOf(amount));
    }
}
