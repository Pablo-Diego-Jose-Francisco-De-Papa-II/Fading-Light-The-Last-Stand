package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class WatchTower extends Building {

    public WatchTower(PlayingArea map, int x, int y) {
        super(map, x, y, 3);
        this.health = 100;  // Example health for Watch Tower
        this.damage = 10;   // Example damage for Watch Tower
        this.range = 5;     // Example range for Watch Tower
        this.cost = 50;     // Example cost for Watch Tower
    }

    @Override
    public void attack(List<Zombie> zombies) {
        // Implement attack logic, such as targeting zombies within range
        for (Zombie zombie : zombies) {
            if (isInRange(zombie)) {
                zombie.takeDamage(this.damage);
            }
        }
    }

    private boolean isInRange(Zombie zombie) {
        // Check if zombie is within the range of the tower
        return Math.abs(zombie.getX() - this.x) <= this.range && Math.abs(zombie.getY() - this.y) <= this.range;
    }
}
