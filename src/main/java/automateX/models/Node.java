package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Base abstract class for all ladder logic nodes
 */
public abstract class Node extends JPanel {
    // Reference to parent rung
    protected Rung rung;
    // Node identifier
    protected int nodeID;

    // Connection references
    protected Node inputNode;
    protected Node outputNode;

    // Node state properties
    protected boolean isActive = false;
    protected boolean isPowered = false;

    // UI properties
    protected Dimension nodeDimension = new Dimension(110, 120);
    protected int margin = 35;

    /**
     * Constructor for Node
     * @param rung Parent rung this node belongs to
     * @param nodeID Unique identifier for this node
     */
    public Node(Rung rung, int nodeID) {
        this.rung = rung;
        this.nodeID = nodeID;

        // Set up UI properties
        setMinimumSize(nodeDimension);
        setPreferredSize(nodeDimension);
        setMaximumSize(nodeDimension);
        setBackground(rung.getBackground());

        // Add ID label
        setLayout(new BorderLayout());
        add(new JLabel("ID : " + nodeID), BorderLayout.NORTH);

        // Set up mouse listeners
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rung.setCurrentNode(Node.this);
                rung.programPanel.setSelectedRung(rung);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    showPopup();
                }
            }
        });
    }

    /**
     * Shows the right-click context menu
     */
    protected void showPopup() {
        JPopupMenu popupMenu = new JPopupMenu();

        // Toggle active status option
        JMenuItem toggleItem = new JMenuItem(isActive ? "Deactivate" : "Activate");
        toggleItem.addActionListener(e -> {
            if (isActive) {
                deactivate();
            } else {
                activate();
            }
            repaint();
            propagateSignal();
        });

        // Delete node option
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            // Remove connections
            if (inputNode != null) inputNode.outputNode = null;
            if (outputNode != null) outputNode.inputNode = null;

            // Remove from rung
            rung.remove(this);
            rung.revalidate();
            rung.repaint();
        });

        popupMenu.add(toggleItem);
        popupMenu.add(deleteItem);

        try {
            popupMenu.show(this, 10, 10);
        } catch (IllegalComponentStateException e) {
            System.out.println("Exception caught");
        }
    }

    /**
     * Set the input node connection
     */
    public void setInputNode(Node inputNode) {
        this.inputNode = inputNode;
        // Update power state when connections change
        updatePowerState();
    }

    /**
     * Set the output node connection
     */
    public void setOutputNode(Node outputNode) {
        this.outputNode = outputNode;
        // Propagate power to the new connection
        propagateSignal();
    }

    /**
     * Get the input node
     */
    public Node getInputNode() {
        return inputNode;
    }

    /**
     * Get the output node
     */
    public Node getOutputNode() {
        return outputNode;
    }

    /**
     * Activate this node
     */
    public abstract void activate();

    /**
     * Deactivate this node
     */
    public abstract void deactivate();

    /**
     * Update power state based on input power and node activation
     */
    public abstract void updatePowerState();

    /**
     * Propagate power signal downstream
     */
    public void propagateSignal() {
        updatePowerState();
        if (outputNode != null) {
            outputNode.updatePowerState();
            outputNode.propagateSignal();
        }
    }

    /**
     * Check if the node is powered
     */
    public boolean isPowered() {
        return isPowered;
    }

    /**
     * Check if the node is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Get input power from previous node or from left rail if first node
     */
    protected boolean getInputPower() {
        if (inputNode == null) {
            // First node in rung is always powered from the left rail
            return true;
        } else {
            return inputNode.isPowered();
        }
    }
}