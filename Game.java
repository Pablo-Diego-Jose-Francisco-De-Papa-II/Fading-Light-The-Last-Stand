import ui.BuildHUD;
import ui.WaveHUD;

import javax.swing.*;
import java.awt.*;

public class Game {

    private final JPanel hudPanel;
    private final BuildHUD buildHUD;
    private final WaveHUD waveHUD;

    public Game() {

        JFrame frame = new JFrame("Fading Light: The Last Stand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        // Create HUDs
        this.buildHUD = new BuildHUD();
        this.waveHUD = new WaveHUD();

        // Set up HUD switcher
        this.hudPanel = new JPanel(new CardLayout());
        this.hudPanel.add(this.buildHUD, "build");
        this.hudPanel.add(this.waveHUD, "wave");

        frame.add(this.hudPanel, BorderLayout.NORTH);
        switchHUD("wave");

        //JButton button = new JButton("I resize!");
        //panel.add(button, BorderLayout.CENTER);

        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public void switchHUD(String mode) {
        CardLayout cl = (CardLayout) this.hudPanel.getLayout();
        cl.show(this.hudPanel, mode);
    }

    public BuildHUD getBuildHUD() {
        return this.buildHUD;
    }

    public WaveHUD getWaveHUD() {
        return this.waveHUD;
    }

}
