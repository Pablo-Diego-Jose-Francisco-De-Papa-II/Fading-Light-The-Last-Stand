package buildings;

import game.PlayingArea;
import slimes.Slime;

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
        super("Watch Tower", map, x, y, 3, // size 50x50 for example
                100,                         // build cost
                3,                           // max level
                100,                         // max health
                50,                          // damage
                100,                         // range (pixels)
                1.0f,                        // attack speed
                watchTowerImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (destroyed) return;
        if (!canAttack()) return;

        for (Slime slime : slimes) {
            if (isInRange(slime)) {
                slime.takeDamage(this.damage);
                break; // attack one slime per attack cycle
            }
        }
    }

    private boolean isInRange(Slime slime) {
        int zx = slime.getX();
        int zy = slime.getY();

        int centerX = this.x + size / 2;
        int centerY = this.y + size / 2;

        int dx = zx - centerX;
        int dy = zy - centerY;
        return (dx * dx + dy * dy) <= (range * range);
    }
}
