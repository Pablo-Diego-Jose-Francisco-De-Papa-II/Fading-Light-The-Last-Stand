package game;

import ui.BuildHUD;
import ui.WaveHUD;

import javax.swing.*;
import java.awt.*;

public class Game {

    private final BuildHUD buildHUD;
    private final WaveHUD waveHUD;
    private final PlayingArea playingArea;
    private final JPanel mapPanel;
    private JFrame frame;

    public Game() {
        frame = new JFrame("Fading Light: The Last Stand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        Image iconImage = new ImageIcon("resources/FadingLight-icon.png").getImage();
        frame.setIconImage(iconImage);

        this.buildHUD = new BuildHUD();
        this.waveHUD = new WaveHUD();
        this.playingArea = new PlayingArea();

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int y = 0; y < 72; y++) {
                    for (int x = 0; x < 128; x++) {
                        Tile tile = playingArea.getTile(x, y);
                        g.drawImage(tile.getImage(), x * 10, y * 10, null);
                    }
                }
            }
        };

        mapPanel.setBounds(0, 0, 1280, 720);
        frame.add(mapPanel);

        frame.add(this.buildHUD);
        frame.add(this.waveHUD);

        switchHUD("off");

        frame.setVisible(true);
    }

    public void switchHUD(String mode) {
        if (mode.equals("build")) {
            buildHUD.setVisible(true);
            waveHUD.setVisible(false);
        } else if (mode.equals("wave")) {
            buildHUD.setVisible(false);
            waveHUD.setVisible(true);
        } else {
            buildHUD.setVisible(false);
            waveHUD.setVisible(false);
        }
    }

    public BuildHUD getBuildHUD() {
        return this.buildHUD;
    }

    public WaveHUD getWaveHUD() {
        return this.waveHUD;
    }
}
