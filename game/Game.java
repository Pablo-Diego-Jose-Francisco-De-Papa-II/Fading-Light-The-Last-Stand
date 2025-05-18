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
    private final WaveManager waveManager;

    private Timer gameLoopTimer;

    private JFrame frame;
    private JLayeredPane layeredPane;

    private boolean waveActive = false;

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
        this.waveManager = new WaveManager(playingArea);
        this.buildHUD = new BuildHUD(this.playingArea, this);
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
        updateHUDs(); // Update HUD values initially
        startGameLoop();
    }

    public void addBuildingToManager(Building building) {
        buildingManager.addBuilding(building);
    }

    /**
     * Switches visibility between build and wave HUD.
     * @param mode "build" or "wave"
     */
    public void switchHUD(String mode) {
        buildHUD.setVisible("build".equals(mode));
        waveHUD.setVisible("wave".equals(mode));
        waveActive = "wave".equals(mode);  // <-- Update waveActive flag here
        layeredPane.repaint();
        updateHUDs();
    }

    public PlayingArea getPlayingArea() {
        return playingArea;
    }

    public BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    private void startGameLoop() {
        gameLoopTimer = new Timer(500, e -> updateGame());
        gameLoopTimer.start();
    }

    public void setGameLoopDelay(int delayMillis) {
        if (gameLoopTimer != null) {
            gameLoopTimer.setDelay(delayMillis);
        }
    }

    private void updateGame() {
        buildingManager.update();
        waveManager.update();
        //buildingManager.cleanup();
        gamePanel.repaint();

        checkTownHallStatus();

        // Update HUD values every tick
        updateHUDs();

        // If wave finished, reward scrap and switch to build HUD automatically
        if (waveActive && waveFinished()) {
            int reward = calculateScrapReward();
            GameState.addScrap(reward);
            GameState.nextDay();
            switchHUD("build");
            waveActive = false;
        }
    }

    private void updateHUDs() {
        int day = GameState.getDayCount();
        int enemiesLeft = calculateEnemiesLeft();
        int scrap = GameState.getScrap();

        // Update build HUD
        buildHUD.updateDay(day);
        buildHUD.updateScrap(scrap);

        // Update wave HUD
        waveHUD.updateDay(day);
        waveHUD.updateScrap(scrap);
        waveHUD.updateEnemiesLeft(enemiesLeft);
    }

    private int calculateEnemiesLeft() {
        // You can customize this if waveManager has appropriate getters
        int remainingPoints = 0;
        if (waveManager != null) {
            remainingPoints = (int) waveManager.getRemainingWavePoints();
        }
        return remainingPoints;
    }

    private boolean waveFinished() {
        // A wave is finished if no slimes remain and no more to spawn
        return waveManager.getSlimes().isEmpty() &&
                waveManager.getNextSlimeIndex() >= waveManager.getSlimesToSpawn().size();
    }

    private int calculateScrapReward() {
        double pointsSpent = waveManager.getCurrentWavePoints() - waveManager.getRemainingWavePoints();
        if (pointsSpent < 0) pointsSpent = 0;
        return (int) pointsSpent; // 1 point = 1 scrap
    }

    public BuildHUD getBuildHUD() {
        return buildHUD;
    }

    public WaveHUD getWaveHUD() {
        return waveHUD;
    }

    public void startWave() {
        waveManager.startNextWave();
        switchHUD("wave");
        waveActive = true;
    }


    private void checkTownHallStatus() {
        Building townHall = buildingManager.getTownHall();

        if (townHall == null || townHall.isDestroyed()) {
            System.out.println("Town Hall destroyed! Ending wave and returning to build mode.");

            // Clear all enemies
            waveManager.getSlimes().clear();

            // Switch to build HUD
            switchHUD("build");
        }
    }
}
