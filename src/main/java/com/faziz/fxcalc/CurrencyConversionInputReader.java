package com.faziz.fxcalc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class CurrencyConversionInputReader {


    public List<ConversionInput> read(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            //Skip the first line.
            br.readLine();
            //Iterate over each line.
            return br.lines().
                map(CurrencyConversionInputReader::readDataLine).collect(toList());
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("data file not found.", ex);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Please provide valid data file", ex);
        }
    }

    private static ConversionInput readDataLine(String line) {
        List<String> csv = of(line.split(",")).map(String::trim).collect(toList());
        return new ConversionInput(csv.get(0), csv.get(1), BigDecimal.valueOf(Double.valueOf(csv.get(2))));
    }
}
