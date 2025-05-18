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

/**
 * Hlavná trieda Game zodpovedná za riadenie hernej logiky a prepínanie medzi režimami stavby a vlny.
 */
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

    /**
     * Konštruktor inicializuje okno hry, všetky komponenty a spúšťa hlavnú hernú slučku.
     */
    public Game() {
        // Inicializácia okna
        this.frame = new JFrame("Fading Light: The Last Stand");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        Image iconImage = new ImageIcon("resources/FadingLight-icon.png").getImage();
        this.frame.setIconImage(iconImage);

        // Inicializácia komponentov
        this.playingArea = new PlayingArea();
        this.buildingManager = this.playingArea.getBuildingManager();
        this.waveManager = new WaveManager(this.playingArea);
        this.buildHUD = new BuildHUD(this.playingArea, this);
        this.waveHUD = new WaveHUD(this);
        this.gamePanel = new GamePanel(this.playingArea, this);

        // Inicializácia vrstveného panelu
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

    /**
     * Prepne HUD podľa aktuálneho režimu hry.
     *
     * @param mode "build" alebo "wave"
     */
    public void switchHUD(String mode) {
        this.buildHUD.setVisible("build".equals(mode));
        this.waveHUD.setVisible("wave".equals(mode));
        this.waveActive = "wave".equals(mode);
        this.layeredPane.repaint();
        this.updateHUDs();
    }

    /**
     * @return WaveManager
     */
    public WaveManager getWaveManager() {
        return this.waveManager;
    }


    /**
     * Spustí hlavnú hernú slučku pomocou Swing Timer-u.
     */
    private void startGameLoop() {
        this.gameLoopTimer = new Timer(500, e -> this.updateGame());
        this.gameLoopTimer.start();
    }

    /**
     * Nastaví meškanie medzi tickmi hernej slučky.
     *
     * @param delayMillis oneskorenie v milisekundách
     */
    public void setGameLoopDelay(int delayMillis) {
        if (this.gameLoopTimer != null) {
            this.gameLoopTimer.setDelay(delayMillis);
        }
    }

    /**
     * Aktualizuje stav hry – logiku budov, vlny, zobrazenie a kontrolu konca hry.
     */
    private void updateGame() {
        this.buildingManager.update();
        this.waveManager.update();
        this.buildingManager.cleanup();
        this.gamePanel.repaint();

        this.checkTownHallStatus();
        this.updateHUDs();

        if (this.waveActive && this.waveFinished()) {
            int reward = this.calculateScrapReward();

            GameState.addScrap(reward);
            GameState.nextDay();

            this.switchHUD("build");
            this.waveActive = false;
        }
    }

    /**
     * Aktualizuje informácie v HUDoch.
     */
    private void updateHUDs() {
        int day = GameState.getDayCount();
        int enemiesLeft = this.calculateEnemiesLeft();
        int scrap = GameState.getScrap();

        this.buildHUD.updateDay(day);
        this.buildHUD.updateScrap(scrap);

        this.waveHUD.updateDay(day);
        this.waveHUD.updateScrap(scrap);
        this.waveHUD.updateEnemiesLeft(enemiesLeft);
    }

    /**
     * Spočíta, koľko nepriateľov zostáva na bojisku.
     *
     * @return Počet nepriateľov, ktorí ešte neboli porazení
     */
    private int calculateEnemiesLeft() {
        int remainingPoints = 0;
        if (this.waveManager != null) {
            remainingPoints = (int)this.waveManager.getRemainingWavePoints();
        }

        return remainingPoints;
    }

    /**
     * Určí, či aktuálna vlna skončila.
     *
     * @return true ak vlna skončila, inak false
     */
    private boolean waveFinished() {
        return this.waveManager.getSlimes().isEmpty() &&
                this.waveManager.getNextSlimeIndex() >= this.waveManager.getSlimesToSpawn().size();
    }

    /**
     * Vypočíta odmenu za skončenú vlnu podľa minutých bodov.
     *
     * @return počet získaného scrapu
     */
    private int calculateScrapReward() {
        double pointsSpent = this.waveManager.getCurrentWavePoints() - this.waveManager.getRemainingWavePoints();
        if (pointsSpent < 0) {
            pointsSpent = 0;
        }
        return (int)pointsSpent;
    }

    /**
     * @return Referencia na BuildHUD
     */
    public BuildHUD getBuildHUD() {
        return this.buildHUD;
    }

    /**
     * Spustí ďalšiu vlnu nepriateľov a prepne režim do „wave“.
     */
    public void startWave() {
        this.waveManager.startNextWave();
        this.switchHUD("wave");
        this.waveActive = true;
    }

    /**
     * Skontroluje stav Town hall. Ak je zničená, ukončí aktuálnu vlnu a prepne hru do stavebného režimu.
     */
    private void checkTownHallStatus() {
        Building townHall = this.buildingManager.getTownHall();

        if (townHall == null || townHall.isDestroyed()) {
            System.out.println("Town Hall destroyed! Ending wave and returning to build mode.");
            this.waveManager.getSlimes().clear();
            this.switchHUD("build");
        }
    }

}
