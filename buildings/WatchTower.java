package buildings;

import game.PlayingArea;
import zombies.Zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class WatchTower extends Building {

    private static BufferedImage watchTowerImage;

    static {
        try {
            watchTowerImage = ImageIO.read(new File("resources/FadingLight-icon.png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image.");
            watchTowerImage = null;
        }
    }

    public WatchTower(PlayingArea map, int x, int y) {
        super("Watch Tower", map, x, y, 3,           // name, map, x, y, size
                100,                                 // build cost
                3,                                   // max level
                100,                                 // max health
                50,                                  // damage
                5,                                   // range
                1.0f,                                // attack speed
                watchTowerImage                      // sprite
        );
    }

    @Override
    public void attack(List<Zombie> zombies) {
        if (destroyed) return;
        if (!canAttack()) return;

        for (Zombie zombie : zombies) {
            if (isInRange(zombie)) {
                zombie.takeDamage(this.damage);
                break;
            }
        }
    }

    private boolean isInRange(Zombie zombie) {
        int zx = zombie.getX();
        int zy = zombie.getY();

        int centerX = this.x + size / 2;
        int centerY = this.y + size / 2;

        int dx = zx - centerX;
        int dy = zy - centerY;
        return (dx * dx + dy * dy) <= (range * range);
    }
}
