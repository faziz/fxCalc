package com.faziz.fxcalc.resources;

import com.faziz.fxcalc.resources.validation.CheckUndefined;
import javax.validation.constraints.NotNull;

public class FxCalcForm {
    
    @NotNull
    @CheckUndefined(message = "ccy1 cannot be \'undefined\'.")
    private String ccy1;
    
    @NotNull
    @CheckUndefined(message = "ccy2 cannot be \'undefined\'.")
    private String ccy2;
    
    @NotNull
    private Double amount;

    /**
     * @return the ccy1
     */
    public String getCcy1() {
        return ccy1;
    }

    /**
     * @param ccy1 the ccy1 to set
     */
    public void setCcy1(String ccy1) {
        this.ccy1 = ccy1;
    }

    /**
     * @return the ccy2
     */
    public String getCcy2() {
        return ccy2;
    }

    /**
     * @param ccy2 the ccy2 to set
     */
    public void setCcy2(String ccy2) {
        this.ccy2 = ccy2;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }    
}
