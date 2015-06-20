package com.kalandyk.graph;

import org.junit.Test;

public class GraphTest {

    private Graph graph;

    @Test
    public void blabla() throws Exception {
        graph = Graph.createRandomGraph(3);
        boolean[][] matrix = new boolean[][]{
                {false, true, true},
                {true, false, true},
                {true, true, false}
        };
        graph.addEdgesFromAdjacjencyMatrix(matrix);
        graph.addEdgeWithValue(0, 1, 10);
        System.out.println(graph.getScript());
    }
}
