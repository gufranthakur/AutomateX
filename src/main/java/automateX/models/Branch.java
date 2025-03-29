package automateX.models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Branch extends JPanel {

    private Rung rung;

    private Dimension branchDimension;

    private final int offset = 30;
    private ArrayList<Node> nodes;



    private Node startNode, endNode;
    private boolean isPowered = false;

    public Branch(Rung rung, int branchID, Node startNode, Node endNode) {
        this.rung = rung;
        this.startNode = startNode;
        this.endNode = endNode;

        branchDimension = new Dimension(getDimensionWidth(), 150);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(branchDimension);
        this.setMaximumSize(branchDimension);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rung.setCurrentBranch(Branch.this);
                rung.programPanel.setSelectedRung(rung);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    showPopup();
                }
            }
        });
        this.add(Box.createHorizontalStrut(offset));
        nodes = new ArrayList<>();

        System.out.println("Branch connected from " + startNode.nodeID + " to " + endNode.nodeID);
    }

    public void addNode(Node node) {
        if (nodes.isEmpty()) {
            node.inputNode = startNode;
            node.outputNode = endNode;
        } else {
            node.inputNode = nodes.getLast();
            nodes.getLast().outputNode = node;
        }
        nodes.add(node);
        this.add(node);

        updatePowerState();

        repaint();
        revalidate();
    }

    public int getDimensionWidth() {
        Node sn = rung.getNodes().get(startNode.nodeID);
        Node en = rung.getNodes().get(endNode.nodeID);

        int distance = en.nodeID - sn.nodeID;

        return distance * 100;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void showPopup() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            // Remove from rung
            rung.branchRung.remove(this);
            rung.getBranches().remove(this);
            rung.revalidate();
            rung.repaint();

            if (rung.getBranches().isEmpty()) rung.setBranchState(false);
        });

        popupMenu.add(deleteItem);

        try {
            popupMenu.show(this, 10, 10);
        } catch (IllegalComponentStateException e) {
            System.out.println("Exception caught");
        }
    }

    public void updatePowerState() {
        this.isPowered = startNode.isPowered();

        if (!nodes.isEmpty()) {
            nodes.getFirst().updatePowerState();
        }
    }

    public void propagateSignal() {
        updatePowerState();

        if (!nodes.isEmpty()) {
            Node firstBranchNode = nodes.getFirst();
            firstBranchNode.updatePowerState();
            firstBranchNode.propagateSignal();
        } else {
            if (isPowered) {
                endNode.updatePowerState();
                endNode.propagateSignal();
            }
        }
    }

    public boolean isPowered() {
        return isPowered;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.WHITE);
        g2D.setStroke(new BasicStroke(2f));

        g2D.drawLine(15, 0, 15, 75);
        g2D.drawLine(15, 75, getWidth() - 15, 75);
        g2D.drawLine(getWidth() - 15, 75, getWidth() - 15, 0);
    }

}
