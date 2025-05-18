package game;

import slimes.Slime;
import slimes.Goob;
import slimes.Goobster;
import slimes.Goobmass;
import slimes.Goober;
import slimes.FastGoob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;


public class WaveManager {
    private final PlayingArea playingArea;
    private final List<Slime> slimes;
    private final Random random;

    private static final int ROWS = 72;
    private static final int COLS = 128;

    private int day = 1;
    private double wavePoints = 100;
    private static final double SCALING_FACTOR = 1.4;

    private double currentWavePoints; // stores wave points at the start of the current wave

    private final Map<String, int[]> slimeTypes = new HashMap<>();

    private final List<String> slimesToSpawn = new ArrayList<>();
    private int nextSlimeIndex = 0;
    private long nextSpawnTime = 0;

    private double remainingWavePoints;

    private final BuildingManager buildingManager;

    public WaveManager(PlayingArea playingArea) {
        this.playingArea = playingArea;
        this.slimes = new ArrayList<>();
        this.random = new Random();

        this.slimeTypes.put("Goob", new int[]{20, 1});
        this.slimeTypes.put("FastGoob", new int[]{30, 2});
        this.slimeTypes.put("Goobster", new int[]{45, 3});
        this.slimeTypes.put("Goobmassa", new int[]{60, 4});
        this.slimeTypes.put("Goober", new int[]{90, 5});

        this.buildingManager = playingArea.getBuildingManager();

        this.remainingWavePoints = 0;
    }

    public void startNextWave() {
        System.out.println("🌙 Starting Day " + this.day + " with " + this.wavePoints + " wave points");

        this.currentWavePoints = this.wavePoints;  // Store current wave points before scaling

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

        this.remainingWavePoints = budget; // leftover budget after spawning

        this.nextSpawnTime = System.currentTimeMillis() + this.random.nextInt(1001);

        this.day++;
        this.wavePoints *= this.SCALING_FACTOR;
    }

    public double getRemainingWavePoints() {
        return this.remainingWavePoints;
    }

    // New getter for current wave points (optional if you want to expose it)
    public double getCurrentWavePoints() {
        return this.currentWavePoints;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (this.nextSlimeIndex < this.slimesToSpawn.size() && currentTime >= this.nextSpawnTime) {
            String slimeType = this.slimesToSpawn.get(this.nextSlimeIndex);
            Slime slime = this.spawnSlimeByType(slimeType);
            if (slime != null) {
                this.slimes.add(slime);

                // ✅ Dôležité: pridaj slima aj do buildingManagera, aby veže mohli útočiť
                this.buildingManager.addSlime(slime);

                System.out.println("Spawned " + slimeType + " at (" + slime.getX() + ", " + slime.getY() + ") with " + slime.getHealth() + " HP");
            }
            this.nextSlimeIndex++;
            this.nextSpawnTime = currentTime + this.random.nextInt(1001);
        }

        for (Slime slime : this.slimes) {
            slime.update();
            System.out.println("Slime at (" + slime.getX() + ", " + slime.getY() + ") has " + slime.getHealth() + " HP");
        }

        this.slimes.removeIf(Slime::isDead);
    }



    private List<String> getAvailableSlimeTypes() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, int[]> entry : this.slimeTypes.entrySet()) {
            if (entry.getValue()[1] <= this.day) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private int getMinSlimeCost(List<String> types) {
        int min = Integer.MAX_VALUE;
        for (String type : types) {
            min = Math.min(min, this.slimeTypes.get(type)[0]);
        }
        return min;
    }

    private Slime spawnSlimeByType(String type) {
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

        return switch (type) {
            case "Goob" -> new Goob(this.playingArea, x, y);
            case "FastGoob" -> new FastGoob(this.playingArea, x, y);
            case "Goobster" -> new Goobster(this.playingArea, x, y);
            case "Goobmassa" -> new Goobmass(this.playingArea, x, y);
            case "Goober" -> new Goober(this.playingArea, x, y);
            default -> null;
        };
    }

    private boolean isValidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= COLS || y >= ROWS) {
            return false;
        }
        Tile tile = this.playingArea.getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    public List<Slime> getSlimes() {
        return this.slimes;
    }

    public int getDay() {
        return this.day;
    }

    public List<String> getSlimesToSpawn() {
        return this.slimesToSpawn;
    }

    public Map<String, int[]> getSlimeTypes() {
        return this.slimeTypes;
    }

    public int getNextSlimeIndex() {
        return this.nextSlimeIndex;
    }

}
