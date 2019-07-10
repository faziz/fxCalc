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
        Graph graph = GraphBuilder.build(fileReader(REF_ARGUMENT));
        CurrencyConverter converter = new CurrencyConverter();

        try (BufferedWriter writer = fileWriter("output.csv")) {
            //Write header.
            writer.write("FromCurrency,ToCurrency,FromAmount,ToAmount");
            writer.newLine();

            //Write the conversion result.
            new CurrencyConversionInputReader().read(fileReader(DATA_ARGUMENT)).
                stream().forEach(i -> {
                    converter.convertAndWrite(graph, i, writer);
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
    
    private void convertAndWrite(Graph graph, ConversionInput input, BufferedWriter writer) {
        Vertex v1 = new Vertex(input.getBaseCurrency());
        Vertex v2 = new Vertex(input.getTargetCurrency());
        Double rate = graph.getRate(v1, v2);
        
        String line = null != rate ? 
                getLineWithConvertedCurrencyAmount(v1, v2, input, rate) : 
                getLineWithWarningMessage(v1, v2, input);
        write(writer, line);
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

    private String getLineWithConvertedCurrencyAmount(Vertex v1, Vertex v2, ConversionInput input, Double rate) {
        BigDecimal convertedCurrency = BigDecimal.valueOf(rate).multiply(input.getFromAmount());
        return format("%s,%s,%s,%.3f", v1.getCurrency(), v2.getCurrency(), 
            input.getFromAmount(), convertedCurrency.doubleValue());
    }

    private String getLineWithWarningMessage(Vertex v1, Vertex v2, ConversionInput input) {
        return format("%s,%s,%s,%s", v1.getCurrency(), v2.getCurrency(), 
            input.getFromAmount(), "Conversion rate missing, conversion not possible.");
    }
}
