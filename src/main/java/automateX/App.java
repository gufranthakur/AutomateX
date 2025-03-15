package automateX;

import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import automateX.core.ControlPanel;
import automateX.core.ProgramPanel;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public JSplitPane splitPane;
    public ProgramPanel programPanel;
    public ControlPanel controlPanel;

    public Font buttonsFont = new Font(FlatInterFont.FAMILY, Font.PLAIN, 20);

    public App() {
        this.setSize(1200, 700);
        this.setTitle("AutomateX");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
    }

    public void init() {
        programPanel = new ProgramPanel(this);
        programPanel.init();


        controlPanel = new ControlPanel(this);
        controlPanel.init();
        controlPanel.initButtons();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                controlPanel.getRootPanel(), programPanel);

    }

    public void addComponent() {
        controlPanel.addComponent();

        this.add(splitPane, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.init();
            app.addComponent();
        });
    }
}
