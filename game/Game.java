package game;

import buildings.Building;
import ui.BuildHUD;
import ui.WaveHUD;

import javax.swing.*;
import java.awt.*;

public class Game {

    private final BuildHUD buildHUD;
    private final WaveHUD waveHUD;
    private final PlayingArea playingArea;
    private final GamePanel gamePanel;
    private final BuildingManager buildingManager;
    private final WaveManager waveManager; // ✅ PRIDANÉ

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
        this.buildingManager = playingArea.getBuildingManager();
        this.waveManager = new WaveManager(playingArea); // ✅ PRIDANÉ
        this.buildHUD = new BuildHUD(this.playingArea, this); // ✅ buildHUD teraz dostane aj this
        this.waveHUD = new WaveHUD(this);
        this.gamePanel = new GamePanel(this.playingArea, this);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1280, 720));
        layeredPane.setLayout(null);

        gamePanel.setBounds(0, 0, 1280, 720);
        layeredPane.add(gamePanel, Integer.valueOf(0));

        buildHUD.setOpaque(false);
        waveHUD.setOpaque(false);
        buildHUD.setBounds(0, 0, 1280, 720);
        waveHUD.setBounds(0, 0, 1280, 720);
        layeredPane.add(buildHUD, Integer.valueOf(100));
        layeredPane.add(waveHUD, Integer.valueOf(100));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setVisible(true);

        switchHUD("build");
        startGameLoop();
    }

    public void addBuildingToManager(Building building) {
        buildingManager.addBuilding(building);
    }

    public void switchHUD(String mode) {
        buildHUD.setVisible("build".equals(mode));
        waveHUD.setVisible("wave".equals(mode));
        layeredPane.repaint();
    }

    public PlayingArea getPlayingArea() {
        return playingArea;
    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public WaveManager getWaveManager() { // ✅ GETTER
        return waveManager;
    }

    private void startGameLoop() {
        Timer timer = new Timer(500, e -> updateGame());
        timer.start();
    }

    private void updateGame() {
        buildingManager.update();
        waveManager.update();
        buildingManager.cleanup();
        gamePanel.repaint();

        checkTownHallStatus();
    }


    public BuildHUD getBuildHUD() {
        return buildHUD;
    }

    public WaveHUD getWaveHUD() {
        return waveHUD;
    }

    public void startWave() {
        waveManager.startNextWave();
    }

    private void checkTownHallStatus() {
        Building townHall = buildingManager.getTownHall();

        if (townHall == null || townHall.isDestroyed()) {
            System.out.println("Town Hall destroyed! Ending wave and returning to build mode.");

            // Vyčisti nepriateľov
            waveManager.getSlimes().clear();

            // Prepni na build mód
            switchHUD("build");
        }
    }

}
