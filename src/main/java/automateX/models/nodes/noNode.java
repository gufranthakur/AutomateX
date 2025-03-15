package automateX.models.nodes;

import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import java.awt.*;

public class noNode extends Node {


    public noNode(Rung rung, int nodeID) {
        super(rung, nodeID);
        this.setLayout(new BorderLayout());
        this.add(new JLabel("ID : " + nodeID), BorderLayout.NORTH);

        isActive = false;
    }

    @Override
    public void execute() {
        if (isActive) {

            this.setBackground(new Color(70, 255, 105));

            if (outputNode == null) return;
            outputNode.isActive = true;
            outputNode.execute();
        }
    }

}
