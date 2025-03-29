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
    private Color activeColor = new Color(96, 126, 250); // Green
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

        g2D.setColor(currentColor);
        g2D.setStroke(new BasicStroke(2f));

        g2D.drawLine(0, getHeight() / 2, margin, getHeight() / 2);
        g2D.drawLine(margin * 2, getHeight() / 2, getWidth(), getHeight() / 2);

        g2D.drawLine(margin, margin, margin, getHeight() - margin);
        g2D.drawLine(margin * 2, margin, margin * 2, getHeight() - margin);

        if (isPowered) {
            g2D.setColor(new Color(255, 215, 0));
            g2D.setStroke(new BasicStroke(1f));
            g2D.drawLine(0, getHeight() - 10, getWidth(), getHeight() - 10);
        }

        if (isBranched) {
            g2D.setColor(Color.WHITE);
            g2D.setStroke(new BasicStroke(2f));
            g2D.drawLine(15, getHeight() / 2, 15, getHeight());
        }
    }


    @Override
    public void activate() {
        isActive = true;
        currentColor = activeColor;
        repaint();
        updatePowerState();
    }

    @Override
    public void deactivate() {
        isActive = false;
        currentColor = inactiveColor;
        repaint();
        updatePowerState();
    }

    @Override
    public void updatePowerState() {
        boolean inputPower = getInputPower();
        isPowered = inputPower && isActive;

        repaint();
    }
}