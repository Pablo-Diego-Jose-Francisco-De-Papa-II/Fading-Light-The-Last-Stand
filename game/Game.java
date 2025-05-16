package game;

import ui.BuildHUD;
import ui.WaveHUD;
import javax.swing.*;
import java.awt.*;

public class Game {
    private final BuildHUD buildHUD;
    private final WaveHUD waveHUD;
    private final PlayingArea playingArea;
    private final GamePanel gamePanel;
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
        this.waveHUD = new WaveHUD(this);
        this.gamePanel = new GamePanel(this.playingArea);

        // Initialize layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1280, 720));
        layeredPane.setLayout(null);

        // Setup GamePanel
        gamePanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(gamePanel, Integer.valueOf(0));

        // Setup HUDs
        buildHUD.setOpaque(false);
        waveHUD.setOpaque(false);
        buildHUD.setBounds(0, 0, 1280, 720);
        waveHUD.setBounds(0, 0, 1280, 720);
        layeredPane.add(buildHUD, Integer.valueOf(100));
        layeredPane.add(waveHUD, Integer.valueOf(100));

        // Add layered pane to frame
        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setVisible(true);

        // Default HUD mode
        switchHUD("build");
    }

    public void switchHUD(String mode) {
        buildHUD.setVisible("build".equals(mode));
        waveHUD.setVisible("wave".equals(mode));
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

    public PlayingArea getPlayingArea() {
        return playingArea;
    }

}
