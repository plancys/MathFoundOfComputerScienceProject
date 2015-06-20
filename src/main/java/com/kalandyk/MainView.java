package com.kalandyk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame implements ActionListener {

    public static final String COMPUTE_LABEL = "Oblicz Cykl Hamiltiona";
    public static final String GENERATE_LABEL = "Generuj graf";
    public static final String NUMBER_OF_VERTICLES_LABEL = "Liczba wierzchołków:";
    public static final String GENERATED_GRAPH_FILEMANE = "one.dot.png";
    public static final String OPERN_EXTERNALY = "Otwórz w zewnętrzynym programie";

    private final BorderLayout borderLayout;
    private final Container container;

    private Button computeButton;
    private Button generateButton;
    private Button openImageButton;
    private JSpinner vertexNumberSpinner;
    private JLabel generatedGraphView;
    private JLabel computedGraphView;

    public MainView() {
        initFrame();
        borderLayout = new BorderLayout();
        container = getContentPane();
        initContent();
        setVisible(true);
    }

    private void initContent() {
        container.setLayout(borderLayout);
        container.add(getTopMenu(), BorderLayout.PAGE_START);
        generatedGraphView = new JLabel(new ImageIcon());
        container.add(generatedGraphView, BorderLayout.WEST);
        computedGraphView = new JLabel(new ImageIcon());
        container.add(computedGraphView, BorderLayout.EAST);
        generateButton.addActionListener(this);
        openImageButton = new Button(OPERN_EXTERNALY);
        openImageButton.addActionListener(this);
        computeButton.addActionListener(this);
        container.add(openImageButton, BorderLayout.PAGE_END);
    }

    private void initFrame() {
        setTitle("Hamiltonian Cycle");
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel getTopMenu() {
        FlowLayout layout = new FlowLayout();
        JPanel menu = new JPanel(layout);
        menu.setLayout(layout);
        menu.add(new JLabel(NUMBER_OF_VERTICLES_LABEL));
        vertexNumberSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
        menu.add(vertexNumberSpinner);
        generateButton = new Button(GENERATE_LABEL);
        menu.add(generateButton);
        computeButton = new Button(COMPUTE_LABEL);
        menu.add(computeButton);
        return menu;
    }

    public void setGeneratedGraph(String filename) throws IOException {
        generatedGraphView.setIcon(new ImageIcon(ImageIO.read(new File(filename))));
    }

    public void setComputedGraph(String filename) throws IOException {
        computedGraphView.setIcon(new ImageIcon(ImageIO.read(new File(filename))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //overridden in Application
    }

    public int getVertexNumber() {
        return (int) vertexNumberSpinner.getModel().getValue();
    }
}
