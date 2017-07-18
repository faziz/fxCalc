package com.faziz.fxcalc.resources.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author faisal
 */
public class CheckUndefinedValidator implements ConstraintValidator<CheckUndefined, String>{

    @Override
    public void initialize(CheckUndefined constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !("undefined".equals(value));
    }
    
}
