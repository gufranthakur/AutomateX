package automateX.models.nodes;

import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import java.awt.*;

public class outputNode extends Node {
    private int margin = 30;
    private Color inactiveColor = Color.WHITE;
    private Color activeColor = new Color(70, 255, 105);
    private Color currentColor = inactiveColor;

    public outputNode(Rung rung, int nodeID) {
        super(rung, nodeID);
        this.setLayout(new BorderLayout());
        this.add(new JLabel("ID : " + nodeID), BorderLayout.NORTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw using current color
        g2D.setColor(currentColor);
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
        g2D.drawLine(margin * 3, getHeight() / 2, getWidth(), getHeight() / 2);

        // If powered, highlight with a brighter color
        if (isPowered) {
            g2D.setColor(new Color(255, 215, 0)); // Gold color for power flow
            g2D.setStroke(new BasicStroke(3f));
            g2D.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

            // Fill coil with light glow when powered
            g2D.setColor(new Color(255, 255, 150, 100));
            g2D.fillOval(margin, margin, margin * 2, arcHeight);
        }
    }

    @Override
    public void activate() {
        isActive = true;
        currentColor = activeColor;
        repaint();
    }

    @Override
    public void deactivate() {
        isActive = false;
        currentColor = inactiveColor;
        repaint();
    }

    @Override
    public void updatePowerState() {
        // Output coil becomes powered if it receives power from input
        boolean inputPower = getInputPower();
        isPowered = inputPower;

        // When output coil is powered, it also becomes active
        if (isPowered) {
            isActive = true;
            currentColor = activeColor;
        } else {
            isActive = false;
            currentColor = inactiveColor;
        }

        System.out.println("Output Node " + nodeID + " - Power state: " + isPowered);
        repaint();
    }
}