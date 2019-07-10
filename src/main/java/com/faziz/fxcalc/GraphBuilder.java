package com.faziz.fxcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import static java.lang.Double.valueOf;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import static java.util.stream.Stream.of;

public class GraphBuilder {

    public static Graph build(Reader reader)
            throws IOException {

        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(reader)) {

            //Read the headers.
            List<String> headers = of(br.readLine().split(",")).
                //Skip the currency column.
                filter(h -> !h.equalsIgnoreCase("currency")).
                    map(String::trim).
                        map(String::toUpperCase).
                            collect(toList());

            //Map of currency labels to currency vertex.
            Map<String, Vertex> vertices = headers.stream().
                map(e -> graph.addVertex(new Vertex(e))).
                    collect(toMap(e -> e.getCurrency(), e -> e));

            //Load forex rates mapping for each currency.
            Map<Vertex, List<String>> csvData = br.lines().
                map(String::toUpperCase).
                    map(line -> data(of(line.split(",")))).
                        collect(toMap(csv -> vertices.get(csv.get(0)), csv -> csv.subList(1, csv.size())));

            //Load Graph with adjacency list.
            csvData.entrySet().forEach(e -> {
                Vertex vertex = e.getKey();
                List<String> data = e.getValue();

                for (int i = 0; i < data.size(); i++) {
                    String d = data.get(i);
                    if (isNumber(d)) {
                        graph.addEdge(vertex, vertices.get(headers.get(i)), valueOf(d));
                    } else {
                        if (vertices.containsKey(d)) {
                            graph.addEdge(vertex, vertices.get(headers.get(i)), vertices.get(d));
                        }
                    }
                }
            });
        }
        return graph;
    }

    private static List<String> data(Stream<String> csv) {
        return csv.map(String::trim).collect(toList());
    }

    private static boolean isNumber(String value) {
        try {
            valueOf(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
