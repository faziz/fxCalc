package com.faziz.fxcalc.fx;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * Provides utilities for number conversion and pattern matching.
 * @author faisal
 */
public class NumberUtil {

    /** Used in testing user input to determine if the process is a number or not. */
    public static final Pattern NUMBER_PATTERN;
    /** Used to parse user input to a number. */
    public static final DecimalFormat FORMAT;
    
    static {
        NUMBER_PATTERN = Pattern.compile("[-+]?\\d+(\\.\\d+)?");
        
        FORMAT = (DecimalFormat) NumberFormat.getInstance();
        FORMAT.setParseBigDecimal(true);
    }
    
}
