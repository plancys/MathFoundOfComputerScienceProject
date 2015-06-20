package com.kalandyk.graph;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

//TODO: sort edges
public class Graph {

    public static final String NEW_LINE = "\n";
    private boolean[][] adjacjencyMatrix;
    private int numberOfNodes;

    Set<Edge> edges = new TreeSet<>();

    private Graph(int numberOfNodes) {
        adjacjencyMatrix = GraphUtil.generateRandomGraph(numberOfNodes);
        addEdgesFromAdjacjencyMatrix(adjacjencyMatrix);
        this.numberOfNodes = numberOfNodes;
    }

    private Graph(boolean adjacencyMatrix[][]) {
        this.adjacjencyMatrix = adjacencyMatrix;
        this.addEdgesFromAdjacjencyMatrix(adjacencyMatrix);
        this.numberOfNodes = adjacencyMatrix.length;
    }


    public static Graph createGraphFromAdjacencyMatrix(boolean adjacencyMatrix[][]) {
        return new Graph(adjacencyMatrix);
    }

    public static Graph createRandomGraph(int n) {
        return new Graph(n);
    }


    public String getScript() {
        StringBuilder builder = new StringBuilder();
        builder.append("Graph { ")
                .append(NEW_LINE);
        edges.forEach(e -> {
            if (e.isHamiltonian()) {
                builder.append(addEdgeWithValue(e.getV(), e.getU(), e.getNumber())).append(NEW_LINE);
            } else {
                builder.append(String.format("%d -- %d", e.getV(), e.getU())).append(NEW_LINE);

            }
        });
        builder.append("}");
        return builder.toString();

    }

    public void addEdge(int u, int v) {
        edges.add(Edge.createEdge(u, v));
    }

    public String addEdgeWithValue(int u, int v, int number) {
        String edge = String.format("%d -- %d [label = \"%d\" color=red]", u, v, number);
//        script.append(edge).append(NEW_LINE);
        return edge;
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

    public boolean getHamiltonianCycle() {
        List<Integer> vector = new ArrayList<>();
        IntStream.range(0, numberOfNodes).forEach(t -> vector.add(t));
        ICombinatoricsVector<Integer> originalVector = Factory.createVector(vector);
        Generator<Integer> permutations = Factory.createPermutationGenerator(originalVector);
        List<Edge> edges = new ArrayList<Edge>();
        for (ICombinatoricsVector<Integer> perm : permutations) {
            if (checkHamiltonianCycle(edges, perm)) {
                return true;
            }
            edges.clear();
        }
        return false;
    }

    private boolean checkHamiltonianCycle(List<Edge> edges, ICombinatoricsVector<Integer> perm) {
        for (int i = 0; i < perm.getSize() - 1; i++) {
            Integer u = perm.getValue(i);
            Integer v = perm.getValue(i + 1);
            edges.add(Edge.createHamiltonianEdge(u, v, i + 1));
            if (adjacjencyMatrix[u][v] == false && adjacjencyMatrix[v][u] == false) {
                return false;
            }
        }
        if (edges.size() == numberOfNodes - 1) {
            edges.forEach(e -> {
                this.edges.remove(e);
                this.edges.add(e);
            });
        }
        return true;
    }

    private boolean edgeExist(boolean[][] matrix, int i, int j) {
        return matrix[i][j] || matrix[j][i];
    }

}
