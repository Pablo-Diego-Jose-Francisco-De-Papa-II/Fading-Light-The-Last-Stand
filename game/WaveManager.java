package game;

import slimes.Slime;
import slimes.Biter;

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

        // Vyber náhodný okraj: 0 = left, 1 = right, 2 = top, 3 = bottom
        int edge = random.nextInt(4);

        switch (edge) {
            case 0: // left
                x = 0;
                y = random.nextInt(720);
                break;
            case 1: // right
                x = 1280 - 1;
                y = random.nextInt(720);
                break;
            case 2: // top
                x = random.nextInt(1280);
                y = 0;
                break;
            case 3: // bottom
                x = random.nextInt(1280);
                y = 720 - 1;
                break;
        }

        if (playingArea.getTile(x, y).isWalkable()) {
            Slime z = new Biter(playingArea, x, y);  // môžeš zmeniť na iný typ
            slimes.add(z);
            System.out.println("Spawned zombie at (" + x + ", " + y + ")");
        }
    }

    public List<Slime> getZombies() {
        return slimes;
    }

    public void startNextWave() {
        // napr. spawni 5 zombíkov pre novú vlnu
        for (int i = 0; i < 5; i++) {
            spawnZombie();
        }

        System.out.println("Started new wave with 5 zombies.");
    }

}
