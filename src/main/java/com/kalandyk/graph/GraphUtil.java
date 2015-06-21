package com.kalandyk.graph;

import java.util.Random;

public class GraphUtil {

    private static Random random = new Random();

    private GraphUtil() {
    }

    public static boolean[][] generateRandomGraph(int vertexCount) {
        boolean adjacenecyMatrix[][] = new boolean[vertexCount][vertexCount];
        int density = Math.abs(random.nextInt() % 4) + 1;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = i; j < vertexCount; j++) {
                if (i != j && random.nextInt() % density == 0) {
                    adjacenecyMatrix[i][j] = true;
                }
            }
            if (isIsolatedNode(adjacenecyMatrix[i])) {
                adjacenecyMatrix[i][Math.abs(random.nextInt() % vertexCount)] = true;
            }
        }
        return adjacenecyMatrix;
    }

    private static boolean isIsolatedNode(boolean[] row) {
        for (boolean edgeExist : row) {
            if (edgeExist) return false;
        }
        return true;
    }
}
