package com.faziz.fxcalc.fx;

import static java.lang.Boolean.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Reads the configured rates and rate matrix from the MS-Excel file and use it for currency conversion.
 * 
 * @author faisal
 */
@Component
public class FxCalculator {
    
    /** Contains rates for currency pairs. */
    @Resource(name = "directRates")
    private Map<String, BigDecimal> directRates;
    /** Provides inter-currency exchange rate calculation method. */
    @Resource(name = "ratesMatrix")
    private Map<String, Map<String, String>> ratesMatrix;
    
    /**
     * Calculates the amount based on the rate matrix.
     * @param input
     * @return
     * @throws IllegalArgumentException if the required number of parameters 
     *  are not provided or parameters are of wrong type.
     */
    public Result calc(String input) 
        throws IllegalArgumentException {
        
        String[] split = input.split(" ");
        if( split.length != 4 ){
            throw new IllegalArgumentException("Incorrect command format.");
        }
        
        String ccy1 = split[0].trim().toUpperCase();
        BigDecimal amount = null;
        if( NumberUtil.NUMBER_PATTERN.matcher(split[1]).matches() ){
            amount = parse( split[1] );
        }
        String ccy2 = split[3].trim().toUpperCase();
        
        return calc(ccy1, ccy2, amount);
    }
    
    /**
     * Converts the amount from ccy1 to ccy2. All three parameters are required.
     * 
     * @param ccy1 The source currency.
     * @param ccy2 The target currency.
     * @param amount The amount in the source currency.
     * @return
     * @throws IllegalArgumentException 
     */
    public Result calc(String ccy1, String ccy2, BigDecimal amount) 
            throws IllegalArgumentException{
        validate(ccy1, ccy2, amount);
        
        Map<String, String> baseCurrency = ratesMatrix.get(ccy1);
        String rateType = baseCurrency.get(ccy2);
        Result result = null;
        
        try{
            result = calc(rateType, ccy1, ccy2, amount);
        }catch(Exception ex){
            String errMsg = String.format("Unable to find rate for %s/%s", ccy1, ccy2);
            result = new Result(amount, FALSE, null, errMsg);
        }
        
        return result;
    }

    /**
     * Validates the three parameters, makes sure the parameter value have been provided.
     * @param ccy1
     * @param ccy2
     * @param amount
     * @throws IllegalArgumentException 
     */
    private void validate(String ccy1, String ccy2, BigDecimal amount) 
            throws IllegalArgumentException {
        
        ccy1 = Optional.ofNullable(ccy1.trim()).orElse("");
        if( ccy1.isEmpty() ){
            throw new IllegalArgumentException("Please provide ccy1.");
        }
        ccy2 = Optional.ofNullable(ccy2.trim()).orElse("");
        if( ccy2.isEmpty() ){
            throw new IllegalArgumentException("Please provide ccy2.");
        }
        if( null == amount ) {
            throw new IllegalArgumentException("Please provide amount.");
        }
    }

    /**
     * 
     * @param rateType
     * @param ccy1
     * @param ccy2
     * @param amount
     * @return 
     */
    private Result calc(String rateType, String ccy1, String ccy2, BigDecimal amount) {
        
        BigDecimal convertedAmount = null;
        String msg = null;
        Result result = null;

        switch(rateType){
            case "D":
                // direct rate * amount
                convertedAmount = directRates.get(ccy1 + ccy2).multiply(amount);
                msg = message(ccy1, ccy2, amount, convertedAmount);
                result = new Result(convertedAmount, TRUE, msg, null);
                break;
            case "INV":
                // 1 / direct rate * amount.
                convertedAmount = inverse(ccy1, ccy2, amount);
                msg = message(ccy2, ccy1, amount, convertedAmount);
                result = new Result(convertedAmount, TRUE, msg, null);
                break;
            case "1:1":
                msg = message(ccy1, ccy2, amount, amount);
                result = new Result(amount, TRUE, msg, null);
                break;
            default:
                //Multiply the amount with the cross rate.
                BigDecimal intermediateAmount = directRates.get(ccy1 + rateType).multiply(amount);
                //Now covert the amount to intended currency rate.
                String crossedRateType = ratesMatrix.get(rateType).get(ccy2);
                //If the cross rate type is INV then calculate the inversed rated amount, 
                 //else just multiply with the direct rate.
                BigDecimal crossRatedAmount = ("INV".equals(crossedRateType)) ? 
                    inverse(rateType, ccy2, intermediateAmount) : 
                    intermediateAmount.
                        multiply(directRates.get(rateType + ccy2));

                msg = message(ccy1, ccy2, amount, crossRatedAmount);
                result = new Result(crossRatedAmount, TRUE, msg, null);
        }
        
        return result;
    }

    /**
     * Calculates the conversion by inverting the direct rate.
     * @param ccy1
     * @param ccy2
     * @param amount
     * @return 
     */
    private BigDecimal inverse(String ccy1, String ccy2, BigDecimal amount) {
        return BigDecimal.ONE.divide(
            directRates.get(ccy2 + ccy1), 10, RoundingMode.HALF_DOWN).
                multiply(amount);
    }
    
    /**
     * Parse the item into a number.
     * @param item
     * @return 
     */
    private BigDecimal parse(String item) {
        BigDecimal d = null;
        try {
            d = (BigDecimal) NumberUtil.FORMAT.parse(item);
        } catch (ParseException ex) {
            System.err.println("Couldn't parse data: "+ item);
        }
        return d;
    }

    /**
     * Prepares the message to display to the user.
     * @param ccy1  Source currency.
     * @param ccy2  Currency to which the amount is to be converted to.
     * @param amount    Amount for the conversion.
     * @param convertedAmount Converted Amount.
     * @return 
     */
    private String message(String ccy1, String ccy2, BigDecimal amount, BigDecimal convertedAmount) {
        return String.format("%s %s = %s %s", ccy1, fmt(ccy1, amount), ccy2, fmt(ccy2, convertedAmount));
    }
    
    /**
     * Format the amount for display.
     * @param currency
     * @param amount
     * @return 
     */
    private BigDecimal fmt(String currency, BigDecimal amount) {
        return amount.setScale(("JPY".equals(currency)) ? 0 : 2, RoundingMode.HALF_DOWN);
    }
}
