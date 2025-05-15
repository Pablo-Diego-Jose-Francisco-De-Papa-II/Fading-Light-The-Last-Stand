package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.util.List;

public class WatchTower extends Building {

    public WatchTower(PlayingArea map, int x, int y) {
        super("Watch Tower", map, x, y, 3,
                100, // build cost
                3,   // max level
                100, // maxHealth initial
                50,  // damage initial
                5,   // range initial
                1.0f // attack speed initial (1 shot per sec)
        );
    }

    @Override
    public void attack(List<Zombie> zombies) {
        if (destroyed) return;

        if (!canAttack()) return;

        for (Zombie zombie : zombies) {
            if (isInRange(zombie)) {
                zombie.takeDamage(this.damage);
                // Attack only one zombie per attack cycle
                break;
            }
        }
    }

    private boolean isInRange(Zombie zombie) {
        int zx = zombie.getX();
        int zy = zombie.getY();

        int centerX = this.x + size / 2;
        int centerY = this.y + size / 2;

        // Simple Euclidean distance or Manhattan distance check
        int dx = zx - centerX;
        int dy = zy - centerY;
        return (dx * dx + dy * dy) <= (range * range);
    }
}
