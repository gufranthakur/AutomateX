package automateX.models;

import automateX.core.ProgramPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Rung extends JPanel {

    public ProgramPanel programPanel;
    private Dimension rungDimension;

    private final int offset = 30;

    private ArrayList<Node> nodes;
    private Node currentNode;

    public Rung(ProgramPanel programPanel) {
        this.programPanel = programPanel;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        rungDimension = new Dimension(1100, 150);

        this.setPreferredSize(rungDimension);
        this.setMaximumSize(rungDimension);
        this.setBackground(programPanel.getBackground().brighter());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                programPanel.setSelectedRung(Rung.this);
            }
        });
        this.add(Box.createHorizontalStrut(offset + 20));

        nodes = new ArrayList<>();

    }

    public void addNode(Node node) {
        if (nodes.isEmpty()) {
            nodes.add(node);

            this.add(node);
        } else {
            node.inputNode = nodes.getLast();
            nodes.getLast().outputNode = node;

            nodes.add(node);

            this.add(node);
        }

        repaint();

    }

    public void setCurrentNode(Node node) {
        this.currentNode = node;
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
        g2D.drawLine(offset, getHeight() / 2, getWidth() - offset, getHeight() / 2);

        g2D.drawLine(offset, 0, offset, getHeight());
        g2D.drawLine(getWidth() - offset, 0, getWidth() - offset, getHeight());
    }

}
