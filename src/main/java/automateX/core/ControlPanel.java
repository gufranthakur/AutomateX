package automateX.core;

import automateX.App;
import automateX.models.Rung;
import automateX.models.nodes.ncNode;
import automateX.models.nodes.noNode;
import automateX.models.nodes.outputNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel {
    private JPanel rootPanel;
    private JTabbedPane tabbedPanel;
    private JPanel controlsPanel;
    private JPanel datafilesPanel;
    private JPanel plcPanel;
    private JPanel runPanel;
    private JButton runButton;
    private JButton saveButton;
    private JComboBox dataTypeBox;
    private JPanel dataContainer;
    private JComboBox functionsBox;
    private JPanel functionContainer;
    private JPanel boxPanel;
    private JButton deleteRungButton;
    private JButton addRungButton;

    private App app;
    public boolean isRunning = false;

    public ArrayList<JButton> userButtons;
        private JButton noButton, ncButton, outputLatchButton;
    public ArrayList<JButton> bitButtons;
    public ArrayList<JButton> timerButtons;
    public ArrayList<JButton> logicGateButtons;
    public ArrayList<JButton> arithmeticButtons;
    public ArrayList<JButton> compareButtons;

    private Dimension buttonDimension = new Dimension(150, 40);

    public ControlPanel(App app) {
        this.app = app;

        userButtons = new ArrayList<>();
    }

    public void init() {

        functionsBox.addItem("User");
        functionsBox.addItem("Bit");
        functionsBox.addItem("Timers");
        functionsBox.addItem("Logic Gates");
        functionsBox.addItem("Arithmetic");
        functionsBox.addItem("Compare");

        functionContainer.setLayout(new BoxLayout(functionContainer, BoxLayout.Y_AXIS));
    }

    public void initButtons() {
        runButton.addActionListener(e -> {
            if (!isRunning) {
                app.execute();
                runButton.setBackground(new Color(234, 75, 75));
                runButton.setText("Stop");
                isRunning = true;

            } else {
                app.stop();
                runButton.setBackground(new Color(5, 186, 9));
                runButton.setText("Run");
                isRunning = false;
            }

        });

        addRungButton.addActionListener(e -> {
            app.programPanel.addRung(new Rung(app.programPanel));
        });

        deleteRungButton.addActionListener(e -> {

        });


        noButton = new JButton("NO Latch");

        noButton.addActionListener(e -> {
            Rung selectedRung = app.programPanel.getSelectedRung();

            if (selectedRung == null) {
                selectedRung = app.programPanel.getRungs().getLast();
            }
            selectedRung.addNode(new noNode(selectedRung, selectedRung.getNodes().size()));
        });

        ncButton = new JButton("NC Latch");
        ncButton.addActionListener(e -> {
            Rung selectedRung = app.programPanel.getSelectedRung();

            if (selectedRung == null) {
                selectedRung = app.programPanel.getRungs().getLast();
            }
            selectedRung.addNode(new ncNode(selectedRung, selectedRung.getNodes().size()));
        });
        outputLatchButton = new JButton("Output Latch");
        outputLatchButton.addActionListener(e -> {
            Rung selectedRung = app.programPanel.getSelectedRung();

            if (selectedRung == null) {
                selectedRung = app.programPanel.getRungs().getLast();
            }
            selectedRung.addNode(new outputNode(selectedRung, selectedRung.getNodes().size()));
        });
    }

    public void addComponent() {
        userButtons.add(noButton);
        userButtons.add(ncButton);
        userButtons.add(outputLatchButton);

        for (JButton button : userButtons) {
            button.setPreferredSize(buttonDimension);
            button.setMaximumSize(buttonDimension);
            button.setBackground(new Color(41, 40, 40));
            button.setFont(app.buttonsFont);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            functionContainer.add(button);
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
