package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class HellstormTurret extends Building {
    public HellstormTurret(PlayingArea map, int x, int y) {
        super(map, x, y);
        this.health = 170;
        this.damage = 15;
        this.range = 3;
        this.cost = 200;
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // Rapid fire
    }
}
