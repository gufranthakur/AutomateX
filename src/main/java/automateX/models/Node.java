package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Node extends JPanel {

    private Rung rung;
    public int nodeID;

    public Node inputNode;
    public Node outputNode;

    public boolean isActive = false;

    private Dimension nodeDimension;

    public Node(Rung rung, int nodeID) {
        this.rung = rung;
        this.nodeID = nodeID;

        nodeDimension = new Dimension(110, 120);
        this.setMinimumSize(nodeDimension);
        this.setPreferredSize(nodeDimension);
        this.setMaximumSize(nodeDimension);
        this.setBackground(rung.getBackground()
        );
        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                rung.setCurrentNode(Node.this);
                rung.programPanel.setSelectedRung(rung);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                checkForPopupTrigger(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                checkForPopupTrigger(e);
            }

            private void checkForPopupTrigger(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    showPopup();
                }
            }
        });

    }

    public void showPopup() {
        JPopupMenu popupMenu = new JPopupMenu();

        // Toggle active status option
        JMenuItem toggleItem = new JMenuItem(isActive ? "Deactivate" : "Activate");
        toggleItem.addActionListener(e -> {
            isActive = !isActive;
            repaint();
        });

        // Delete node option
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            // Remove connections
            if (inputNode != null) inputNode.outputNode = null;
            if (outputNode != null) outputNode.inputNode = null;

            // Remove from rung
            rung.remove(this);
        });

        popupMenu.add(toggleItem);
        popupMenu.add(deleteItem);

        try {
            popupMenu.show(this, 10, 10);
        } catch (IllegalComponentStateException e) {
            System.out.println("Exception caught");
        }

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
