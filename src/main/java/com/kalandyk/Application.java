package com.kalandyk;

import com.kalandyk.graph.Graph;
import com.kalandyk.graph.GraphPrinter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static com.kalandyk.graph.GraphType.SOURCE;

public class Application {
    private MainView mainView;
    private Graph graph;

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
        String command = e.getActionCommand();
        if (command.equals(MainView.GENERATE_LABEL)) {
            graph = Graph.createRandomGraph(mainView.getVertexNumber());
            GraphPrinter.printGraph(graph, SOURCE.name());
            mainView.setGeneratedGraph(SOURCE.getImageFileName());
        } else if (command.equals(MainView.COMPUTE_LABEL)) {
            if (graph.getHamiltonianCycle()) {
                GraphPrinter.printGraph(graph, SOURCE.name());
                mainView.setGeneratedGraph(SOURCE.getImageFileName());
            }
        } else {
            Desktop.getDesktop().open(new File(""));
        }
    }


}
