package com.kalandyk;

import com.kalandyk.graph.Graph;
import com.kalandyk.graph.GraphUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;

public class Application {
    private MainView mainView;

    public Application() {
        EventQueue.invokeLater(() -> {
            this.mainView = new MainView() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        handleAction(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
        });
    }

    public static void main(String[] args) {
        Application application = new Application();
    }

    public void handleAction(ActionEvent e) throws Exception {
        if (e.getActionCommand().equals(MainView.GENERATE_LABEL)) {
            int vertexNumber = mainView.getVertexNumber();
            boolean[][] adjacencyMatrix = GraphUtil.generateRandomGraph(vertexNumber);
            Graph graph = new Graph(adjacencyMatrix);
            String graphScript = graph.getScript();
            PrintWriter one = new PrintWriter("one.dot", "UTF-8");
            one.print(graphScript);
            one.close();
            Runtime.getRuntime().exec("dot -T png -O  " + "one.dot");
            Thread.sleep(500);
            mainView.setGeneratedGraph("one.dot.png");
        }
    }


}
