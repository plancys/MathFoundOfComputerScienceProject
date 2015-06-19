package com.kalandyk.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

//TODO: sort edges
public class Graph {

    public static final String NEW_LINE = "\n";
    private StringBuilder script;

    List<Edge> edges = new ArrayList<>();

    public Graph() {
        script = new StringBuilder();
        script.append("graph {").append(NEW_LINE);
    }

    public String getScript() {
        return script.append("}").toString();
    }

    public void addEdge(int u, int v) {
        String edge = String.format("%d -- %d", u, v);
        script.append(edge).append(NEW_LINE);
    }

    public void addEdgeWithValue(int u, int v, int number) {
        String edge = String.format("%d -- %d [label = \"%d\" color=red]", u, v, number);
        script.append(edge).append(NEW_LINE);
    }

    public void addEdgesFromAdjacjencyMatrix(boolean[][] matrix) {
        int length = matrix.length;
        IntStream.range(0, length).forEach(first -> {
            IntStream.range(first, length).forEach(second -> {
                if (edgeExist(matrix, first, second)) {
                    addEdge(first, second);
                }
            });
        });
    }

    private void removeEdge(boolean[][] matrix, Edge edge) {
        matrix[edge.getU()][edge.getV()] = false;
        matrix[edge.getU()][edge.getV()] = false;
    }

    private boolean edgeExist(boolean[][] matrix, int i, int j) {
        return matrix[i][j] || matrix[j][i];
    }

}
