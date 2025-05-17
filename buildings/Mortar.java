package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Mortar extends Building {

    private static BufferedImage mortarImage;

    static {
        try {
            mortarImage = ImageIO.read(new File("resources/buildings/mortar.png"));
        } catch (IOException e) {
            System.err.println("Failed to load mortar image.");
            mortarImage = null;
        }
    }

    public Mortar(PlayingArea map, int x, int y) {
        super("Mortar", map, x, y, 4, 130, 2, 120, 30, 130, 1.2f, mortarImage);
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
