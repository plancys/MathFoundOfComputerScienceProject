package com.kalandyk;

import com.kalandyk.graph.Graph;
import com.kalandyk.graph.GraphPrinter;
import com.kalandyk.graph.GraphType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static com.kalandyk.graph.GraphType.SOLVED;
import static com.kalandyk.graph.GraphType.SOURCE;

public class Application {
    private MainView mainView;
    private Graph graph;
    private GraphType currentGraph = null;

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
            applyGraphToView(SOURCE);
        } else if (command.equals(MainView.COMPUTE_LABEL)) {
            boolean isHamiltonianCycle = graph.existHamiltonianCycle();
            if (isHamiltonianCycle) {
                applyGraphToView(SOLVED);
            }
        } else {
            Desktop.getDesktop().open(new File(currentGraph.getImageFileName()));
        }
    }

    private void applyGraphToView(GraphType type) throws Exception {
        setImageView(type);
    }

    private void setImageView(GraphType type) throws Exception {
        GraphPrinter.printGraph(graph, type.name());
        mainView.setGeneratedGraph(type.getImageFileName());
        currentGraph = type;
    }
}
