package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HellstormTurret extends Building {

    private static BufferedImage hellstormImage;

    static {
        try {
            hellstormImage = ImageIO.read(new File("resources/buildings/hellstorm.png"));
        } catch (IOException e) {
            System.err.println("Failed to load hellstorm turret image.");
            hellstormImage = null;
        }
    }

    public HellstormTurret(PlayingArea map, int x, int y) {
        super("Hellstorm Turret", map, x, y, 3, 200, 2, 170, 18, 90, 3.0f, hellstormImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (destroyed || !canAttack()) return;

        for (int i = 0; i < 2 && i < slimes.size(); i++) {
            Slime slime = slimes.get(i);
            if (isInRange(slime)) {
                slime.takeDamage(this.damage);
            }
        }
    }

    private boolean isInRange(Slime slime) {
        int dx = slime.getX() - (x + size / 2);
        int dy = slime.getY() - (y + size / 2);
        return dx * dx + dy * dy <= range * range;
    }
}
