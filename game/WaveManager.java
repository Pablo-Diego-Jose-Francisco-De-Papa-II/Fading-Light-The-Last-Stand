package game;

import slimes.Slime;
import slimes.Goob;
import slimes.Goobster;
import slimes.Goobmass;
import slimes.Goober;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

/**
 * Trieda WaveManager zodpovedá za správu vĺn útokov slimov v hre.
 * Obsahuje logiku pre generovanie nových vĺn, výber typov nepriateľov,
 * ich postupné spúšťanie a aktualizáciu počas hry.
 */
public class WaveManager {
    private final PlayingArea playingArea;
    private final List<Slime> slimes;
    private final Random random;

    private static final int ROWS = 72;
    private static final int COLS = 128;

    private int day = 1;
    private double wavePoints = 100;
    private static final double SCALING_FACTOR = 1.4;

    private double currentWavePoints;

    private final Map<String, int[]> slimeTypes = new HashMap<>();

    private final List<String> slimesToSpawn = new ArrayList<>();
    private int nextSlimeIndex = 0;
    private long nextSpawnTime = 0;

    private double remainingWavePoints;

    private final BuildingManager buildingManager;

    /**
     * Konštruktor pre WaveManager. Inicializuje mapu typov slimov
     * a nastavuje počiatočné hodnoty vlny.
     *
     * @param playingArea herná plocha
     */
    public WaveManager(PlayingArea playingArea) {
        this.playingArea = playingArea;
        this.slimes = new ArrayList<>();
        this.random = new Random();

        this.slimeTypes.put("Goob", new int[]{20, 1});
        this.slimeTypes.put("Goobster", new int[]{45, 2});
        this.slimeTypes.put("Goobmassa", new int[]{60, 3});
        this.slimeTypes.put("Goober", new int[]{90, 4});

        this.buildingManager = playingArea.getBuildingManager();

        this.remainingWavePoints = 0;
    }

    /**
     * Spustí ďalšiu vlnu nepriateľov, podľa aktuálneho dňa a bodov vlny.
     * Náhodne vygeneruje poradie nepriateľov, ktoré budú postupne spawnované.
     */
    public void startNextWave() {
        this.currentWavePoints = this.wavePoints;

        double budget = this.wavePoints;
        List<String> availableTypes = this.getAvailableSlimeTypes();

        this.slimesToSpawn.clear();
        this.nextSlimeIndex = 0;

        while (budget >= this.getMinSlimeCost(availableTypes)) {
            String selectedType = availableTypes.get(this.random.nextInt(availableTypes.size()));
            int cost = this.slimeTypes.get(selectedType)[0];

            if (cost <= budget) {
                this.slimesToSpawn.add(selectedType);
                budget -= cost;
            }
        }

        this.remainingWavePoints = budget;

        this.nextSpawnTime = System.currentTimeMillis() + this.random.nextInt(1001);

        this.day++;
        this.wavePoints *= this.SCALING_FACTOR;
    }

    /**
     * @return počet zostávajúcich bodov vlny po naplánovaní spawnov
     */
    public double getRemainingWavePoints() {
        return this.remainingWavePoints;
    }

    /**
     * @return počet bodov použiteľných v aktuálnej vlne
     */
    public double getCurrentWavePoints() {
        return this.currentWavePoints;
    }

    /**
     * Aktualizuje stav slizov a spawnuje ďalších, ak je čas.
     * Volá update pre všetkých živých slizov a odstraňuje mŕtvych.
     */
    public void update() {
        long currentTime = System.currentTimeMillis();

        if (this.nextSlimeIndex < this.slimesToSpawn.size() && currentTime >= this.nextSpawnTime) {

            Slime slime = this.spawnSlimeByType(this.random.nextInt(4));
            if (slime != null) {
                this.slimes.add(slime);
                this.buildingManager.addSlime(slime);
            }

            this.nextSlimeIndex++;
            this.nextSpawnTime = currentTime + this.random.nextInt(1001);
        }

        for (Slime slime : this.slimes) {
            slime.update();
        }

        for (int i = this.slimes.size() - 1; i >= 0; i--) {
            Slime slime = this.slimes.get(i);
            if (slime.isDead()) {
                this.slimes.remove(i);
            }
        }

    }

    /**
     * Zistí, ktoré typy slizov sú odomknuté podľa aktuálneho dňa.
     *
     * @return zoznam názvov odomknutých slizov
     */
    private List<String> getAvailableSlimeTypes() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, int[]> entry : this.slimeTypes.entrySet()) {
            if (entry.getValue()[1] <= this.day) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    /**
     * Zistí najnižšiu cenu slizu zo zoznamu typov.
     *
     * @param types dostupné typy slizov
     * @return minimálna cena
     */
    private int getMinSlimeCost(List<String> types) {
        int min = Integer.MAX_VALUE;
        for (String type : types) {
            min = Math.min(min, this.slimeTypes.get(type)[0]);
        }
        return min;
    }

    /**
     * Vytvorí nového slizu podľa zadaného čísla typu a náhodnej pozície na okraji mapy.
     *
     * @param num číslo typu slizu (0–3)
     * @return inštancia slizu alebo null ak pozícia nie je platná
     */
    private Slime spawnSlimeByType(int num) {
        int x = 0;
        int y = 0;
        int edge = this.random.nextInt(4);

        switch (edge) {
            case 0 -> {
                x = 0;
                y = this.random.nextInt(ROWS);
            }
            case 1 -> {
                x = COLS - 1;
                y = this.random.nextInt(ROWS);
            }
            case 2 -> {
                x = this.random.nextInt(COLS);
                y = 0;
            }
            case 3 -> {
                x = this.random.nextInt(COLS);
                y = ROWS - 1;
            }
        }

        if (!this.isValidTile(x, y)) {
            return null;
        }

        return switch (num) {
            case 0 -> new Goob(this.playingArea, x, y);
            case 1 -> new Goobster(this.playingArea, x, y);
            case 2 -> new Goobmass(this.playingArea, x, y);
            case 3 -> new Goober(this.playingArea, x, y);
            default -> null;
        };
    }

    /**
     * Overí, či je možné na zadaných súradniciach vytvoriť slima.
     *
     * @param x súradnica X
     * @param y súradnica Y
     * @return true ak je dlaždica platná a priechodná
     */
    private boolean isValidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= COLS || y >= ROWS) {
            return false;
        }

        Tile tile = this.playingArea.getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    /**
     * @return zoznam všetkých živých slimov
     */
    public List<Slime> getSlimes() {
        return this.slimes;
    }

    /**
     * @return zoznam mien slimov naplánovaných na spawn vo vlne
     */
    public List<String> getSlimesToSpawn() {
        return this.slimesToSpawn;
    }

    /**
     * @return index ďalšieho slima, ktorý má byť spawnutý
     */
    public int getNextSlimeIndex() {
        return this.nextSlimeIndex;
    }
}
