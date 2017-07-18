package com.faziz.fxcalc.resources.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUndefinedValidator.class)
@Documented
public @interface CheckUndefined {

    String message() default "Value cannot be undefined.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
