package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Ballista extends Building {

    private static BufferedImage ballistaImage;

    static {
        try {
            ballistaImage = ImageIO.read(new File("resources/buildings/ballista.png"));
        } catch (IOException e) {
            System.err.println("Failed to load ballista image.");
            ballistaImage = null;
        }
    }

    public Ballista(PlayingArea map, int x, int y) {
        super("Ballista", map, x, y, 4, 120, 2, 140, 35, 120, 1.5f, ballistaImage);
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
