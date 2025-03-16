package automateX.models.nodes;

import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import java.awt.*;

public class outputNode extends Node {

    int margin = 30;
    Color color = Color.WHITE;

    public outputNode(Rung rung, int nodeID) {
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

        // Left horizontal line
        g2D.drawLine(0, getHeight() / 2, margin, getHeight() / 2);

        // Left parenthesis
        int arcWidth = margin;
        int arcHeight = getHeight() - 2 * margin;
        g2D.drawArc(margin, margin, arcWidth, arcHeight, 90, 180);

        // Right parenthesis
        g2D.drawArc(margin * 2, margin, arcWidth, arcHeight, 270, 180);

        // Right horizontal line
        g2D.drawLine(margin * 3, getHeight() / 2, 150, getHeight() / 2);
    }

    @Override
    public void activate() {
        color = (new Color(70, 255, 105));
    }

    @Override
    public void deactivate() {
        color = Color.WHITE;
    }
}
