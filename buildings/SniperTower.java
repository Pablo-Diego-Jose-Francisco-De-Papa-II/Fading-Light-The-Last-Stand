package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class SniperTower extends Building {
    public SniperTower(PlayingArea map, int x, int y) {
        super(map, x, y);
        this.health = 80;
        this.damage = 50;
        this.range = 7;
        this.cost = 150;
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // Long-range high damage
    }
}
