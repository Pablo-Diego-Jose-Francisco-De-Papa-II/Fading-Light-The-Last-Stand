package game;

import slimes.Slime;
import slimes.Goob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveManager {
    private PlayingArea playingArea;
    private List<Slime> slimes;
    private Random random;

    public WaveManager(PlayingArea playingArea) {
        this.playingArea = playingArea;
        this.slimes = new ArrayList<>();
        this.random = new Random();
    }

    public void spawnZombie() {
        int x = 0, y = 0;

        int edge = random.nextInt(4);

        switch (edge) {
            case 0: x = 0; y = random.nextInt(128); break;         // left
            case 1: x = 128 - 1; y = random.nextInt(72); break; // right
            case 2: x = random.nextInt(128); y = 0; break;          // top
            case 3: x = random.nextInt(128); y = 72 - 1; break; // bottom
        }

        if (isValidTile(x, y)) {
            Slime z = new Goob(playingArea, x, y); // môžeš použiť iný typ
            slimes.add(z);
            System.out.println("Spawned zombie at (" + x + ", " + y + ")");
        } else {
            System.err.println("❌ Invalid spawn position at (" + x + ", " + y + ")");
        }
    }

    private boolean isValidTile(int x, int y) {
        if (x < 0 || y < 0 || x >= 128 || y >= 72) {
            return false;
        }

        Tile tile = playingArea.getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    public List<Slime> getZombies() {
        return slimes;
    }

    public void startNextWave() {
        for (int i = 0; i < 5; i++) {
            spawnZombie();
        }
        System.out.println("Started new wave with 5 zombies.");
    }

    public void startFirstWave() {
        spawnZombie();
        System.out.println("Started first wave.");
    }

    public void update() {
        for (Slime slime : slimes) {
            slime.update();
        }

        slimes.removeIf(Slime::isDead);
    }

}
