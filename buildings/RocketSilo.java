package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class RocketSilo extends Building {
    public RocketSilo(PlayingArea map, int x, int y) {
        super(map, x, y);
        this.health = 200;
        this.damage = 70;
        this.range = 6;
        this.cost = 300;
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // Devastating area attack
    }
}
