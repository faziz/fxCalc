package com.faziz.fxcalc;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import java.math.BigDecimal;
import java.util.Properties;

public class CurrencyConverter {
    
    public static final String REF_ARGUMENT = "ref";
    public static final String DATA_ARGUMENT = "data";

    /**
     * @param args the command line arguments
     * @throws IllegalArgumentException
     * @throws java.io.IOException
     */
    public static void main(String[] args) 
            throws IllegalArgumentException, IOException {

        //Make sure the required parameters are passed.
        validateArguments();
        CurrencyConverter converter = new CurrencyConverter();

        try (BufferedWriter writer = fileWriter("output.csv")) {
            //Write header.
            converter.write(writer, "FromCurrency,ToCurrency,FromAmount,ToAmount");

            //Write the conversion result.
            new CurrencyConversionInputReader().read(fileReader(DATA_ARGUMENT)).
                stream().forEach(i -> {
                    converter.convertAndWrite(i, writer);
                });
        }
    }

    private static BufferedWriter fileWriter(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }

    private static FileReader fileReader(String fileName) throws FileNotFoundException {
        return new FileReader(getProperty(fileName));
    }
    
    private static void validateArguments() throws IllegalArgumentException {

        Properties properties = System.getProperties();
        if (!properties.containsKey("ref")) {
            throw new IllegalArgumentException("Please provide reference data using the paramter \'ref\'");
        }
        if (!properties.containsKey("data")) {
            throw new IllegalArgumentException("Please provide currency data using the paramter \'data\'");
        }
    }
    
    private void convertAndWrite(ConversionInput input, BufferedWriter writer) 
        throws IllegalStateException {

        try {
            Vertex baseCurrency   = new Vertex(input.getBaseCurrency());
            Vertex targetCurrency = new Vertex(input.getTargetCurrency());

            Graph graph = GraphBuilder.build(fileReader(REF_ARGUMENT));
            Double rate = graph.getRate(baseCurrency, targetCurrency);

            String line = null != rate ?
                    getLineWithConvertedCurrencyAmount(baseCurrency, targetCurrency, input, rate) :
                    getLineWithWarningMessage(baseCurrency, targetCurrency, input);
            write(writer, line);
        } catch (IllegalStateException ex) {
            throw ex;
        } catch (IOException | IllegalArgumentException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private void write(BufferedWriter writer, String string) 
            throws IllegalStateException {
        try {
            writer.write(string);
            writer.newLine();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private String getLineWithConvertedCurrencyAmount(Vertex baseCurrency, 
            Vertex targetCurrency, ConversionInput input, Double rate) {
        BigDecimal convertedCurrency = BigDecimal.valueOf(rate).multiply(input.getFromAmount());
        return format("%s,%s,%s,%.3f", baseCurrency.getCurrency(), targetCurrency.getCurrency(), 
            input.getFromAmount(), convertedCurrency.doubleValue());
    }

    private String getLineWithWarningMessage(Vertex baseCurrency, 
            Vertex targetCurrency, ConversionInput input) {
        return format("%s,%s,%s,%s", baseCurrency.getCurrency(), targetCurrency.getCurrency(), 
            input.getFromAmount(), "Conversion rate missing, conversion not possible.");
    }
}
