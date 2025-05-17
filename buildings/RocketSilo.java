package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RocketSilo extends Building {

    private static BufferedImage rocketSiloImage;

    static {
        try {
            rocketSiloImage = ImageIO.read(new File("resources/buildings/rocketsilo.png"));
        } catch (IOException e) {
            System.err.println("Failed to load rocket silo image.");
            rocketSiloImage = null;
        }
    }

    public RocketSilo(PlayingArea map, int x, int y) {
        super("Rocket Silo", map, x, y, 5, 300, 1, 200, 70, 150, 0.2f, rocketSiloImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (destroyed || !canAttack()) return;

        for (Slime slime : slimes) {
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
