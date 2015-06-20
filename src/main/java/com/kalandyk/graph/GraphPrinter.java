package com.kalandyk.graph;

import java.io.PrintWriter;

public class GraphPrinter {

    private GraphPrinter() {

    }

    public static void printGraph(Graph graph, String fileName) throws Exception {
        PrintWriter printer = new PrintWriter(fileName, "UTF-8");
        printer.print(graph.getScript());
        printer.close();
        Process process = Runtime.getRuntime().exec("dot -T png -O  " + fileName);
        process.waitFor();
    }

}
