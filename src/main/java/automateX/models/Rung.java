package automateX.models;

import automateX.core.ProgramPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Rung extends JPanel {

    public ProgramPanel programPanel;
    private Dimension rungDimension;
    private Border selectedBorder = new LineBorder(new Color(37, 97, 209));
    private Border emptyBorder = new EmptyBorder(0, 0, 0, 0);

    private JPanel mainRung;
    private JPanel branchRung;

    private final int offset = 30;

    private ArrayList<Node> nodes;
    private Node currentNode;

    private ArrayList<Branch> branches;

    public Rung(ProgramPanel programPanel) {
        this.programPanel = programPanel;

        initRungConfigurations();
        initPanels();

        nodes = new ArrayList<>();
        branches = new ArrayList<>();
    }

    private void initRungConfigurations() {
        rungDimension = new Dimension(1100, 300);
        this.setLayout(new BorderLayout());
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
    }

    private void initPanels() {
        mainRung = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;

                g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2D.setColor(Color.WHITE);
                g2D.setStroke(new BasicStroke(2f));
                g2D.drawLine(offset, getHeight() / 2, getWidth() - offset, getHeight() / 2);

                g2D.drawLine(offset, 0, offset, getHeight());
                g2D.drawLine(getWidth() - offset, 0, getWidth() - offset, getHeight());
            }
        };
        mainRung.setLayout(new BoxLayout(mainRung, BoxLayout.X_AXIS));
        mainRung.add(Box.createHorizontalStrut(offset + 20));
        mainRung.setPreferredSize(new Dimension(1100, 150));
        mainRung.setMaximumSize(new Dimension(1100, 150));

        branchRung = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;

                g2D.setColor(Color.WHITE);
                g2D.setStroke(new BasicStroke(2f));

                g2D.drawLine(offset, 0, offset, getHeight());
                g2D.drawLine(getWidth() - offset, 0, getWidth() - offset, getHeight());
            }
        };
        branchRung.setLayout(new BoxLayout(branchRung, BoxLayout.X_AXIS));
        branchRung.add(Box.createHorizontalStrut(offset + 20));
        branchRung.setPreferredSize(new Dimension(1100, 150));
        branchRung.setMaximumSize(new Dimension(1100, 150));

        this.add(mainRung, BorderLayout.CENTER);
        this.add(branchRung, BorderLayout.SOUTH);

        setBranchState(false);
    }

    public void addNode(Node node) {
        if (!nodes.isEmpty()) {
            node.inputNode = nodes.getLast();
            nodes.getLast().outputNode = node;
        }
        nodes.add(node);
        mainRung.add(node);
        repaint();

    }

    public void setCurrentNode(Node node) {
        this.currentNode = node;
        for (Rung r : programPanel.getRungs()){
            for (Node n : r.getNodes()) {
                n.setBorder(emptyBorder);
            }
        }
        node.setBorder(selectedBorder);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setBranchState(boolean branchState) {
        if (branchState) {
            mainRung.setPreferredSize(new Dimension(1100, 150));
            mainRung.setMaximumSize(new Dimension(1100, 150));
            branchRung.setPreferredSize(new Dimension(1100, 150));
            branchRung.setMaximumSize(new Dimension(1100, 150));

            branchRung.setVisible(true);

            rungDimension = new Dimension(1100, 300);
        } else {
            mainRung.setPreferredSize(new Dimension(1100, 150));
            mainRung.setMaximumSize(new Dimension(1100, 150));

            branchRung.setVisible(false);

            rungDimension = new Dimension(1100, 150);
        }

        this.setPreferredSize(rungDimension);
        this.setMaximumSize(rungDimension);

        repaint();
        revalidate();
    }

    public void addBranch(Node startNode, Node endNode) {
        Branch branch = new Branch(this, branches.size(), startNode, endNode);

        branches.add(branch);

    }

}