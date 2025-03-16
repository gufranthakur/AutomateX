package automateX.models.nodes;

import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import java.awt.*;

public class noNode extends Node {

    private int margin = 35;
    private Color color = new Color(255, 255, 255);

    public noNode(Rung rung, int nodeID) {
        super(rung, nodeID);
        this.setLayout(new BorderLayout());
        this.add(new JLabel("ID : " + nodeID), BorderLayout.NORTH);

        isActive = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(2f));

        if (!isActive) {
            g2D.drawLine(0, getHeight() / 2, margin, getHeight() / 2);
            g2D.drawLine(margin, margin, margin, getHeight() - margin);
            g2D.drawLine(margin * 2, margin, margin * 2, getHeight() - margin);
            g2D.drawLine(margin * 2, getHeight() / 2, 150, getHeight() / 2);
        } else {
            g2D.drawLine(0, getHeight() / 2, margin, getHeight() / 2);
            g2D.drawLine(margin, margin, margin, getHeight() - margin);

            g2D.drawLine(margin, margin, margin * 2, margin * 2 + (margin/2));

            g2D.drawLine(margin * 2, margin, margin * 2, getHeight() - margin);
            g2D.drawLine(margin * 2, getHeight() / 2, 150, getHeight() / 2);
        }
    }

    @Override
    public void activate() {
        System.out.println("Node " + nodeID + " Activated");
        isActive = true;

        color = (new Color(70, 255, 105));

        if (outputNode == null) return;
        if (outputNode instanceof noNode) return;
        outputNode.isActive = true;
        outputNode.activate();

    }

    @Override
    public void deactivate() {
        isActive = false;

        color = Color.WHITE;
        if (outputNode == null) return;

        outputNode.isActive = false;
        outputNode.deactivate();
    }

}
