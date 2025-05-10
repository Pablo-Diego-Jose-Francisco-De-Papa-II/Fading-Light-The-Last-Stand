package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class Ballista extends Building {
    public Ballista(PlayingArea map, int x, int y) {
        super(map, x, y);
        this.health = 150;
        this.damage = 30;
        this.range = 4;
        this.cost = 100;
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // Pierce logic
    }
}
