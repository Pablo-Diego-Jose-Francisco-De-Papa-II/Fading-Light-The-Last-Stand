package game;

import buildings.Building;
import ui.BuildHUD;
import ui.WaveHUD;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Image;

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
        this.frame = new JFrame("Fading Light: The Last Stand");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        Image iconImage = new ImageIcon("resources/FadingLight-icon.png").getImage();
        this.frame.setIconImage(iconImage);

        this.playingArea = new PlayingArea();
        this.buildingManager = this.playingArea.getBuildingManager();
        this.waveManager = new WaveManager(this.playingArea);
        this.buildHUD = new BuildHUD(this.playingArea, this);
        this.waveHUD = new WaveHUD(this);
        this.gamePanel = new GamePanel(this.playingArea, this);

        this.layeredPane = new JLayeredPane();
        this.layeredPane.setPreferredSize(new Dimension(1280, 720));
        this.layeredPane.setLayout(null);

        this.gamePanel.setBounds(0, 0, 1280, 720);
        this.layeredPane.add(this.gamePanel, Integer.valueOf(0));

        this.buildHUD.setOpaque(false);
        this.waveHUD.setOpaque(false);
        this.buildHUD.setBounds(0, 0, 1280, 720);
        this.waveHUD.setBounds(0, 0, 1280, 720);
        this.layeredPane.add(this.buildHUD, Integer.valueOf(100));
        this.layeredPane.add(this.waveHUD, Integer.valueOf(100));

        this.frame.setContentPane(this.layeredPane);
        this.frame.pack();
        this.frame.setVisible(true);

        this.switchHUD("build");
        this.updateHUDs();
        this.startGameLoop();
    }

    public void switchHUD(String mode) {
        this.buildHUD.setVisible("build".equals(mode));
        this.waveHUD.setVisible("wave".equals(mode));
        this.waveActive = "wave".equals(mode);  // <-- Update waveActive flag here
        this.layeredPane.repaint();
        this.updateHUDs();
    }

    public WaveManager getWaveManager() {
        return this.waveManager;
    }

    private void startGameLoop() {
        this.gameLoopTimer = new Timer(500, e -> this.updateGame());
        this.gameLoopTimer.start();
    }

    public void setGameLoopDelay(int delayMillis) {
        if (this.gameLoopTimer != null) {
            this.gameLoopTimer.setDelay(delayMillis);
        }
    }

    private void updateGame() {
        this.buildingManager.update();
        this.waveManager.update();
        //buildingManager.cleanup();
        this.gamePanel.repaint();

        this.checkTownHallStatus();

        // Update HUD values every tick
        this.updateHUDs();

        // If wave finished, reward scrap and switch to build HUD automatically
        if (this.waveActive && this.waveFinished()) {
            int reward = this.calculateScrapReward();
            GameState.addScrap(reward);
            GameState.nextDay();
            this.switchHUD("build");
            this.waveActive = false;
        }
    }

    private void updateHUDs() {
        int day = GameState.getDayCount();
        int enemiesLeft = this.calculateEnemiesLeft();
        int scrap = GameState.getScrap();

        // Update build HUD
        this.buildHUD.updateDay(day);
        this.buildHUD.updateScrap(scrap);

        // Update wave HUD
        this.waveHUD.updateDay(day);
        this.waveHUD.updateScrap(scrap);
        this.waveHUD.updateEnemiesLeft(enemiesLeft);
    }

    private int calculateEnemiesLeft() {
        // You can customize this if waveManager has appropriate getters
        int remainingPoints = 0;
        if (this.waveManager != null) {
            remainingPoints = (int)this.waveManager.getRemainingWavePoints();
        }
        return remainingPoints;
    }

    private boolean waveFinished() {
        // A wave is finished if no slimes remain and no more to spawn
        return this.waveManager.getSlimes().isEmpty() &&
                this.waveManager.getNextSlimeIndex() >= this.waveManager.getSlimesToSpawn().size();
    }

    private int calculateScrapReward() {
        double pointsSpent = this.waveManager.getCurrentWavePoints() - this.waveManager.getRemainingWavePoints();
        if (pointsSpent < 0) {
            pointsSpent = 0;
        }
        return (int)pointsSpent;
    }

    public BuildHUD getBuildHUD() {
        return this.buildHUD;
    }

    public void startWave() {
        this.waveManager.startNextWave();
        this.switchHUD("wave");
        this.waveActive = true;
    }


    private void checkTownHallStatus() {
        Building townHall = this.buildingManager.getTownHall();

        if (townHall == null || townHall.isDestroyed()) {
            System.out.println("Town Hall destroyed! Ending wave and returning to build mode.");

            // Clear all enemies
            this.waveManager.getSlimes().clear();

            // Switch to build HUD
            this.switchHUD("build");
        }
    }
}
