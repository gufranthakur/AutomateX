package automateX.core;

import automateX.App;
import automateX.models.Node;
import automateX.models.Rung;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class ProgramPanel extends JPanel {

    public App app;
    public JScrollPane scrollPane;

    private ArrayList<Rung> rungs;
    private Rung selectedRung;

    private Border selectedBorder = new LineBorder(new Color(37, 97, 209));
    private Border emptyBorder = new EmptyBorder(0, 0, 0, 0);

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
        for (Rung r : rungs) r.setBorder(emptyBorder);
        selectedRung.setBorder(selectedBorder);
    }

    public Rung getSelectedRung() {
        return selectedRung;
    }

    public ArrayList<Rung> getRungs() {
        return rungs;
    }



}
