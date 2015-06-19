package com.kalandyk;

import com.kalandyk.graph.Graph;
import com.kalandyk.graph.GraphUtil;
import com.kalandyk.graph.Edge;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) throws IOException {
        int N = 5;
        boolean[][] matrix = GraphUtil.generateRandomGraph(N);

        ICombinatoricsVector<Integer> originalVector = Factory.createVector(new Integer[]{0, 1, 2, 3, 4});
        Generator<Integer> gen = Factory.createPermutationGenerator(originalVector);

        String source = "one.dot";
        drawGraph(matrix, source);
        // Print the result
        List<Edge> hamiltonianCycle = getHamiltonianCycle(N, matrix, gen);


        PrintWriter two = new PrintWriter("two.dot", "UTF-8");


        final Graph finalGraph = new Graph();
        hamiltonianCycle.forEach(edge -> {
            finalGraph.addEdgeWithValue(edge.getU(), edge.getV(), edge.getNumber());
            removeEdge(matrix, edge);
        });

        finalGraph.addEdgesFromAdjacjencyMatrix(matrix);
        two.print(finalGraph.getScript());
        two.close();


        Runtime.getRuntime().exec("dot -T png -O  two.dot");


//        try {
//            String fileName = "graph.dot";
//            String fileName2 = "graph2.dot";
//            final PrintWriter writer = new PrintWriter(fileName, "UTF-8");
//            writer.println("graph {");
//            PrintWriter writer2 = new PrintWriter(fileName2, "UTF-8");
//            writer2.println("graph {");
//            writeEdges(N, matrix, writer2);
//            hamiltonianCycle.forEach(edge -> {
//                writer.println(generateEdgeWithValue(edge));
//                removeEdge(matrix, edge);
//            });
//            writeEdges(N, matrix, writer);
//            writer.println("}");
//            writer.close();
//            writer2.println("}");
//            writer2.close();
//            Runtime.getRuntime().exec("dot -T png -O " + fileName);
//            Runtime.getRuntime().exec("dot -T png -O " + fileName2);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void drawGraph(boolean[][] matrix, String source) throws IOException {
        Graph graph = new Graph();
        graph.addEdgesFromAdjacjencyMatrix(matrix);
        PrintWriter one = new PrintWriter("one.dot", "UTF-8");
        one.print(graph.getScript());
        one.close();
        Runtime.getRuntime().exec("dot -T png -O  " + source);
    }

    private static void writeEdges(int n, boolean[][] matrix, PrintWriter writer) {
        IntStream.range(1, n).forEach(valueI -> {
            IntStream.range(valueI, n).forEach(valueJ -> {
                if (edgeExist(matrix, valueI, valueJ)) {
                    writer.println(printEdge(valueI, valueJ));
                }
            });
        });
    }

    private static String printEdge(int i, int j) {
        return String.format("%d -- %d", i, j);
    }

    private static String generateEdgeWithValue(Edge edge) {
        return String.format("%d -- %d [label = \"%d\"]", edge.getV(), edge.getU(), edge.getNumber());
    }

    private static void removeEdge(boolean[][] matrix, Edge edge) {
        matrix[edge.getU()][edge.getV()] = false;
        matrix[edge.getV()][edge.getU()] = false;
    }

    private static boolean edgeExist(boolean[][] matrix, int i, int j) {
        return matrix[i][j] || matrix[j][i];
    }

    private static List<Edge> getHamiltonianCycle(int n, boolean[][] matrix, Generator<Integer> permutations) {
        List<Edge> edges = new ArrayList<Edge>();
        for (ICombinatoricsVector<Integer> perm : permutations) {
            if (checkHamiltonianCycle(n, matrix, edges, perm)) {
                return edges;
            }
            edges.clear();
        }
        return Collections.emptyList();
    }

    private static boolean checkHamiltonianCycle(int n, boolean[][] matrix, List<Edge> edges, ICombinatoricsVector<Integer> perm) {
        for (int i = 0; i < perm.getSize() - 1; i++) {
            Integer u = perm.getValue(i);
            Integer v = perm.getValue(i + 1);
            edges.add(Edge.createHamiltonianEdge(u, v, i + 1));
            if (matrix[u][v] == false && matrix[v][u] == false) {
                return false;
            }
        }
        return true;
    }
}
