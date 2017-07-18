package com.faziz.fxcalc.resources;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author faisal
 */
public class FxCalcFormTest {

    private static ValidatorFactory vf;
    private static Validator validator;
    private static FxCalcForm fxCalcForm;

    @BeforeClass
    public static void setup() {

        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();

        fxCalcForm = new FxCalcForm();
        fxCalcForm.setAmount(null);
        fxCalcForm.setCcy1("undefined");
        fxCalcForm.setCcy2("undefined");
    }

    @Test
    public void testValidateFxFormCalc() {
        Set<ConstraintViolation<FxCalcForm>> violations = validator.validate(fxCalcForm);
        assertTrue(!violations.isEmpty());
    }
}
