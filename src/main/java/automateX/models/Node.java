package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Node extends JPanel {

    private Rung rung;

    private Node inputNode;
    private Node outputNode;

    private Dimension nodeDimension;

    public Node(Rung rung) {
        this.rung = rung;

        nodeDimension = new Dimension(150, 120);
        this.setMinimumSize(nodeDimension);
        this.setPreferredSize(nodeDimension);
        this.setMaximumSize(nodeDimension);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                rung.setCurrentNode(Node.this);
            }
        });

    }

    public void startConnection() {
        rung.establishConnection(this);
    }

    public Node getInputNode() {
        return inputNode;
    }

    public void setInputNode(Node inputNode) {
        this.inputNode = inputNode;
    }

    public Node getOutputNode() {
        return outputNode;
    }

    public void setOutputNode(Node outputNode) {
        this.outputNode = outputNode;
    }

    public abstract void execute();

}
