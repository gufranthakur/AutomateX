package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Branch extends JPanel {

    private Rung rung;

    private Dimension branchDimension;

    private final int offset = 30;
    private ArrayList<Node> nodes;
    private Node startNode, endNode;

    public Branch(Rung rung, int branchID, Node startNode, Node endNode) {
        this.rung = rung;
        this.startNode = startNode;
        this.endNode = endNode;

        branchDimension = new Dimension(getDimensionWidth(), 100);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(branchDimension);
        this.setBackground(rung.getBackground());

        nodes = new ArrayList<>();

        System.out.println("Branch connected from " + startNode.nodeID + " to " + endNode.nodeID);
    }

    public void addNode(Node node) {
        if (nodes.isEmpty()) {
            node.inputNode = startNode;
            node.outputNode = endNode;
        } else {
            node.inputNode = nodes.getLast();
            nodes.getLast().outputNode = node;
        }
        nodes.add(node);
        this.add(node);

        repaint();
        revalidate();
    }

    public int getDimensionWidth() {
        Node sn = rung.getNodes().get(startNode.nodeID);
        Node en = rung.getNodes().get(endNode.nodeID);

        int distance = en.nodeID - sn.nodeID;

        return distance * 100;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.WHITE);
        g2D.setStroke(new BasicStroke(2f));

        g2D.drawLine(15, 0, 15, 50);
        g2D.drawLine(15, 50, getWidth() - 15, 50);
        g2D.drawLine(getWidth() - 15, 50, getWidth() - 15, 0);
    }

}
