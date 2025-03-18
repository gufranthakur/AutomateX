package automateX.models.nodes;

import automateX.models.Node;
import automateX.models.Rung;

import java.awt.*;

/**
 * Normally Open Contact Node
 * Conducts power when activated, blocks power when deactivated
 */
public class noNode extends Node {
    private Color inactiveColor = Color.WHITE;
    private Color activeColor = new Color(70, 255, 105); // Green
    private Color currentColor = inactiveColor;

    public noNode(Rung rung, int nodeID) {
        super(rung, nodeID);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set color based on active state
        g2D.setColor(currentColor);
        g2D.setStroke(new BasicStroke(2f));

        // Draw horizontal power lines
        g2D.drawLine(0, getHeight() / 2, margin, getHeight() / 2);
        g2D.drawLine(margin * 2, getHeight() / 2, getWidth(), getHeight() / 2);

        // Draw vertical contact lines
        g2D.drawLine(margin, margin, margin, getHeight() - margin);
        g2D.drawLine(margin * 2, margin, margin * 2, getHeight() - margin);

        // If node is powered, draw in a brighter color
        if (isPowered) {
            g2D.setColor(new Color(255, 215, 0)); // Gold color for power flow
            g2D.setStroke(new BasicStroke(3f));
            g2D.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    }

    @Override
    public void activate() {
        isActive = true;
        currentColor = activeColor;
        updatePowerState();
        repaint();
    }

    @Override
    public void deactivate() {
        isActive = false;
        currentColor = inactiveColor;
        updatePowerState();
        repaint();
    }

    @Override
    public void updatePowerState() {
        // Normally Open: Pass power when active AND receiving power
        boolean inputPower = getInputPower();
        isPowered = inputPower && isActive;

        System.out.println("NO Node " + nodeID + " - Input Power: " + inputPower +
                ", Active: " + isActive + ", Output Power: " + isPowered);

        repaint();
    }
}