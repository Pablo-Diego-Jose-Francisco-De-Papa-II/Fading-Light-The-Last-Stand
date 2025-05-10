package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class WatchTower extends Building {
    public WatchTower(PlayingArea map, int x, int y) {
        super(map, x, y);
        this.health = 100;
        this.damage = 10;
        this.range = 3;
        this.cost = 50;
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // attack logic
    }
}

