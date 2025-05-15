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
    private JLayeredPane layeredPane;

    public Game() {
        frame = new JFrame("Fading Light: The Last Stand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Image iconImage = new ImageIcon("resources/FadingLight-icon.png").getImage();
        frame.setIconImage(iconImage);

        this.playingArea = new PlayingArea();
        this.buildHUD = new BuildHUD(this.playingArea);
        this.waveHUD = new WaveHUD();

        // Initialize layered pane and set its layout to null for absolute positioning
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1280, 720));
        layeredPane.setLayout(null);

        // Create the mapPanel (tile map drawing)
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

        // Set bounds for mapPanel and add at bottom layer (0)
        mapPanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(mapPanel, Integer.valueOf(0));

        // Prepare HUD panels
        // Make sure HUDs are transparent if you want the map visible behind them
        buildHUD.setOpaque(false);
        waveHUD.setOpaque(false);

        // Set bounds of HUD panels (you can adjust height as needed)
        buildHUD.setBounds(0, 0, 1280, 720);
        waveHUD.setBounds(0, 0, 1280, 720);

        // Add HUD panels on higher layer (e.g., 100)
        layeredPane.add(buildHUD, Integer.valueOf(100));
        layeredPane.add(waveHUD, Integer.valueOf(100));

        // Add layered pane to frame's content pane
        frame.setContentPane(layeredPane);

        // Initially hide both HUDs (or set your default)
        switchHUD("build");

        frame.pack();
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
        // Refresh to make sure visibility changes show up immediately
        layeredPane.repaint();
    }

    public void addScrap(int amount) {
        int current = buildHUD.getShop().getMoney();
        buildHUD.getShop().addMoney(amount);
        buildHUD.updateScrap(current + amount);
    }

    public BuildHUD getBuildHUD() {
        return this.buildHUD;
    }

    public WaveHUD getWaveHUD() {
        return this.waveHUD;
    }
}
