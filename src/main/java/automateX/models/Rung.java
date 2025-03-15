package automateX.models;

import automateX.core.ProgramPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Rung extends JPanel {

    private ProgramPanel programPanel;
    private Dimension rungDimension;

    private final int offset = 30;

    private ArrayList<Node> nodes;
    private Node currentNode;

    public Rung(ProgramPanel programPanel) {
        this.programPanel = programPanel;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        rungDimension = new Dimension(3000, 150);

        this.setPreferredSize(rungDimension);
        this.setMaximumSize(rungDimension);
        this.setBackground(programPanel.getBackground().brighter());
        this.add(Box.createHorizontalStrut(offset));

        nodes = new ArrayList<>();

        //addNode(new Node(this));

    }

    public void addNode(Node node) {
        if (nodes.isEmpty()) {
            nodes.add(node);
            this.add(Box.createHorizontalStrut(20));
            this.add(node);
        } else {
            nodes.add(node);
            node.startConnection();
            this.add(Box.createHorizontalStrut(20));
            this.add(node);
        }

        repaint();

    }

    public void establishConnection(Node node) {
        node.setOutputNode(currentNode);
        currentNode.setInputNode(node);


    }

    public void setCurrentNode(Node node) {
        this.currentNode = node;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.WHITE);
        g2D.setStroke(new BasicStroke(2f));
        g2D.drawLine(offset, getHeight() / 2, getWidth() - offset, getHeight() / 2);

        g2D.drawLine(offset, 0, offset, getHeight());
        g2D.drawLine(getWidth() - offset, 0, getWidth() - offset, getHeight());
    }

}
