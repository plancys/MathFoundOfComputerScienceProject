package com.kalandyk;

import com.google.common.base.Stopwatch;
import com.kalandyk.graph.Graph;
import com.kalandyk.graph.GraphPrinter;
import com.kalandyk.graph.GraphType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.kalandyk.graph.GraphType.SOURCE;

public class Application {
    private MainView mainView;
    private Graph graph;
    private GraphType currentGraph = null;
    public static volatile boolean isComputing = false;

    private Thread computingThread = null;
    private Thread countingThread = null;

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
            applyGraphToView(graph, mainView, SOURCE, this);
            mainView.hideMessage();
        } else if (command.equals(MainView.COMPUTE_LABEL)) {
            Stopwatch started = Stopwatch.createStarted();
            if (computingThread != null) {
                computingThread.interrupt();
            }
            computingThread = new Thread(() -> {
                isComputing = true;
                boolean isHamiltonianCycle = graph.existHamiltonianCycle();
                isComputing = false;
                if (isHamiltonianCycle) {
                    try {
                        applyGraphToView(graph, mainView, SOURCE, this);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    isComputing = false;
                    mainView.showMessage("Brak cyklu Hamiltona!");
                }
            }
            );
            computingThread.start();

            if (countingThread != null) {
                countingThread.interrupt();
            }
            countingThread = new Thread(() -> {
                while (isComputing) {
                    try {
                        String time = String.format("%d:%02d", started.elapsed(TimeUnit.MINUTES), started.elapsed(TimeUnit.SECONDS) % 60);
                        System.out.println(time);
                        mainView.showMessage("Upłynęło: " + time);
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    if (started.elapsed(TimeUnit.SECONDS) > 60) {
                        computingThread.interrupt();
                        mainView.showMessage("Upłynął limit czasu. Obliczenia anulowano");
                        isComputing = false;
                    }
                }
            }
            );
            countingThread.start();

        } else {
            Desktop.getDesktop().open(new File(currentGraph.getImageFileName()));
        }
    }

    public static void applyGraphToView(Graph graph, MainView mainView, GraphType type, Application application) throws Exception {
        setImageView(graph, mainView, type, application);
    }

    private static void setImageView(Graph graph, MainView mainView, GraphType type, Application application) throws Exception {
        GraphPrinter.printGraph(graph, type.name());
        mainView.setGeneratedGraph(type.getImageFileName());
        application.currentGraph = type;
    }
}
