package automateX.core;

import automateX.App;
import automateX.models.Rung;

import javax.swing.*;
import java.util.ArrayList;

public class ProgramPanel extends JPanel {

    public App app;

    private ArrayList<Rung> rungs;

    public ProgramPanel(App app) {
        this.app = app;
        this.setSize(1000, 1000);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        rungs = new ArrayList<>(20);
        addRung(new Rung(this));
    }

    public void addRung(Rung rung) {
        rungs.add(rung);
        this.add(rung);
    }

    public void init() {

    }

    public ArrayList<Rung> getRungs() {
        return rungs;
    }

}
