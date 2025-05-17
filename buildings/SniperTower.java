package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SniperTower extends Building {

    private static BufferedImage sniperTowerImage;

    static {
        try {
            sniperTowerImage = ImageIO.read(new File("resources/buildings/sniper.png"));
        } catch (IOException e) {
            System.err.println("Failed to load sniper tower image.");
            sniperTowerImage = null;
        }
    }

    public SniperTower(PlayingArea map, int x, int y) {
        super("Sniper Tower", map, x, y, 3, 160, 3, 90, 55, 180, 0.8f, sniperTowerImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (destroyed || !canAttack()) return;

        for (Slime slime : slimes) {
            if (isInRange(slime)) {
                slime.takeDamage(this.damage);
                break;
            }
        }
    }

    private boolean isInRange(Slime slime) {
        int dx = slime.getX() - (x + size / 2);
        int dy = slime.getY() - (y + size / 2);
        return dx * dx + dy * dy <= range * range;
    }
}
