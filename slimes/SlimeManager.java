package slimes;

import java.util.ArrayList;
import game.PlayingArea;

public class SlimeManager {
    private final ArrayList<Slime> slimes = new ArrayList<>();
    private final PlayingArea map;

    public SlimeManager(PlayingArea map) {
        this.map = map;
    }

    public void spawnZombie(Slime slime) {
        this.slimes.add(slime);
    }

    public void updateZombies() {
        for (int i = 0; i < this.slimes.size(); i++) {
            Slime z = this.slimes.get(i);
            z.update(); // ← teraz pohyb + útok v jednom

            if (z.getHealth() <= 0) {
                z.die();
                this.slimes.remove(i);
                i--;
            }
        }
    }


    public ArrayList<Slime> getZombies() {
        return this.slimes;
    }
}
