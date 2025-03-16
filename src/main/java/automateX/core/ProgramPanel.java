package automateX.core;

import automateX.App;
import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import java.util.ArrayList;

public class ProgramPanel extends JPanel {

    public App app;
    public JScrollPane scrollPane;

    private ArrayList<Rung> rungs;
    private Rung selectedRung;

    public ProgramPanel(App app) {
        this.app = app;
        this.setSize(600, 1000);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        rungs = new ArrayList<>(20);
        addRung(new Rung(this));

        scrollPane = new JScrollPane(this);
    }

    public void addRung(Rung rung) {
        rungs.add(rung);
        this.add(rung);
    }

    public void init() {

    }

    public void setSelectedRung(Rung rung) {
        this.selectedRung = rung;
    }

    public Rung getSelectedRung() {
        return selectedRung;
    }

    public ArrayList<Rung> getRungs() {
        return rungs;
    }

}
