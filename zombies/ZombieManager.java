package zombies;

import java.util.ArrayList;
import game.PlayingArea;

public class ZombieManager {
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final PlayingArea map;

    public ZombieManager(PlayingArea map) {
        this.map = map;
    }

    public void spawnZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }

    public void updateZombies() {
        for (int i = 0; i < this.zombies.size(); i++) {
            Zombie z = this.zombies.get(i);
            z.move();
            z.dealDamage();
            if (z.getHealth() <= 0) {
                z.die();
                this.zombies.remove(i);
                i--;
            }
        }
    }

    public ArrayList<Zombie> getZombies() {
        return this.zombies;
    }
}
