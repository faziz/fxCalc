package com.faziz.fxcalc.fx;

import java.math.BigDecimal;

/**
 * Contains the result of conversion.
 * @author faisal
 */
public class Result {
    
    /** The resulting amount after the conversion. **/
    private final BigDecimal covertedAmount;
    /** Indicates if the calculation was successful or not. **/
    private final Boolean successful;
    /** Error message if conversion fails. **/
    private final String errorMessage;
    /** Message to show after successful conversion. **/
    private final String message;

    /**
     * @return the covertedAmount
     */
    public BigDecimal getCovertedAmount() {
        return covertedAmount;
    }

    /**
     * @return the isSuccessful
     */
    public Boolean isSuccessful() {
        return successful;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public Result(BigDecimal covertedAmount, Boolean successful, 
            String message, String errorMessage) {
        this.covertedAmount = covertedAmount;
        this.successful = successful;
        this.message = message;
        this.errorMessage = errorMessage;
    }
}
