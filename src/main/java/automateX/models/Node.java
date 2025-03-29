package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Node extends JPanel {
    protected Rung rung;
    protected int nodeID;
    protected Node inputNode;
    protected Node outputNode;
    protected boolean isActive = false;
    protected boolean isPowered = false;

    public boolean isBranched = false;

    protected Dimension nodeDimension = new Dimension(100, 100);
    protected int margin = 30;

    public Node(Rung rung, int nodeID) {
        this.rung = rung;
        this.nodeID = nodeID;

        setMinimumSize(nodeDimension);
        setPreferredSize(nodeDimension);
        setMaximumSize(nodeDimension);
        setBackground(rung.getBackground());

        setLayout(new BorderLayout());
        add(new JLabel("ID : " + nodeID), BorderLayout.NORTH);

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

    protected void showPopup() {
        JPopupMenu popupMenu = new JPopupMenu();

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

        JMenuItem createBranchItem = new JMenuItem("Create branch");
        createBranchItem.addActionListener( e -> {
            rung.startBranchCreation(Node.this);
        });

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            if (inputNode != null) inputNode.outputNode = null;
            if (outputNode != null) outputNode.inputNode = null;

            rung.remove(this);
            rung.revalidate();
            rung.repaint();
        });

        popupMenu.add(toggleItem);
        popupMenu.add(createBranchItem);
        popupMenu.addSeparator();
        popupMenu.add(deleteItem);

        try {
            popupMenu.show(this, 10, 10);
        } catch (IllegalComponentStateException e) {
            System.out.println("Exception caught");
        }
    }

    public abstract void activate();

    public abstract void deactivate();

    public abstract void updatePowerState();

    public void propagateSignal() {
        updatePowerState();
        if (outputNode != null) {
            outputNode.updatePowerState();
            outputNode.propagateSignal();
        }
    }

    public boolean isPowered() {
        return isPowered;
    }

    protected boolean getInputPower() {
        if (inputNode == null) {
            return true;
        } else {
            return inputNode.isPowered();
        }
    }


}