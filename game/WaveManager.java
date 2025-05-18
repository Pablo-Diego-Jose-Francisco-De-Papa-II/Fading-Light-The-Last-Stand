package game;

import slimes.*;

import java.util.*;

public class WaveManager {
    private final PlayingArea playingArea;
    private final List<Slime> slimes;
    private final Random random;

    private static final int ROWS = 72;
    private static final int COLS = 128;

    private int day = 1;
    private double wavePoints = 100;
    private final double SCALING_FACTOR = 1.4;

    private final Map<String, SlimeType> slimeTypes = new HashMap<>();

    public WaveManager(PlayingArea playingArea) {
        this.playingArea = playingArea;
        this.slimes = new ArrayList<>();
        this.random = new Random();

        // Nastavenie typov slimes
        slimeTypes.put("Goob", new SlimeType(20, 1));
        slimeTypes.put("FastGoob", new SlimeType(30, 2));
        slimeTypes.put("Goobster", new SlimeType(45, 3));
        slimeTypes.put("Goobmassa", new SlimeType(60, 4));
        slimeTypes.put("Goober", new SlimeType(90, 5));
    }

    public void startNextWave() {
        System.out.println("🌙 Starting Day " + day + " with " + (int) wavePoints + " wave points");

        double budget = wavePoints;
        List<String> availableTypes = getAvailableSlimeTypes();

        while (budget >= getMinSlimeCost(availableTypes)) {
            String selectedType = availableTypes.get(random.nextInt(availableTypes.size()));
            SlimeType typeInfo = slimeTypes.get(selectedType);

            if (typeInfo.cost <= budget) {
                Slime slime = spawnSlimeByType(selectedType);
                if (slime != null) {
                    slimes.add(slime);
                    budget -= typeInfo.cost;
                    System.out.println("Spawned " + selectedType + " (" + typeInfo.cost + " pts). Remaining: " + (int) budget);
                }
            }
        }

        day++;
        wavePoints *= SCALING_FACTOR;
    }

    private List<String> getAvailableSlimeTypes() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, SlimeType> entry : slimeTypes.entrySet()) {
            if (entry.getValue().minWave <= day) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    private int getMinSlimeCost(List<String> types) {
        int min = Integer.MAX_VALUE;
        for (String type : types) {
            min = Math.min(min, slimeTypes.get(type).cost);
        }
        return min;
    }

    private Slime spawnSlimeByType(String type) {
        int x = 0, y = 0;
        int edge = random.nextInt(4);

        switch (edge) {
            case 0 -> { x = 0; y = random.nextInt(ROWS); }
            case 1 -> { x = COLS - 1; y = random.nextInt(ROWS); }
            case 2 -> { x = random.nextInt(COLS); y = 0; }
            case 3 -> { x = random.nextInt(COLS); y = ROWS - 1; }
        }

        if (!isValidTile(x, y)) return null;

        return switch (type) {
            case "Goob" -> new Goob(playingArea, x, y);
            case "FastGoob" -> new FastGoob(playingArea, x, y);
            case "Goobster" -> new Goobster(playingArea, x, y);
            case "Goobmassa" -> new Goobmass(playingArea, x, y);
            case "Goober" -> new Goober(playingArea, x, y);
            default -> null;
        };
    }

    private boolean isValidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= COLS || y >= ROWS) return false;
        Tile tile = playingArea.getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    public List<Slime> getSlimes() {
        return slimes;
    }

    public void update() {
        for (Slime slime : slimes) {
            slime.update();
        }
        slimes.removeIf(Slime::isDead);
    }

    // 💡 Pomocná trieda pre popis typu slima
    private static class SlimeType {
        int cost;
        int minWave;

        SlimeType(int cost, int minWave) {
            this.cost = cost;
            this.minWave = minWave;
        }
    }
}
