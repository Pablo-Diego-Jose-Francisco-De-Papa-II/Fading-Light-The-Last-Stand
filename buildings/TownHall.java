package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TownHall extends Building {

    private static BufferedImage townHallImage;

    static {
        try {
            townHallImage = ImageIO.read(new File("resources/townhall.png"));
        } catch (IOException e) {
            System.err.println("Failed to load town hall image.");
            townHallImage = null;
        }
    }

    public TownHall(PlayingArea map, int x, int y) {
        super("Town Hall", map, x, y,
                6,      // size (maybe larger than other buildings)
                0,        // no build cost (it's placed at game start)
                1,        // level (no upgrade maybe)
                1000,      // high health
                0,        // no attack damage
                0,        // no range
                0.0f,     // no attack speed
                townHallImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        // Town Hall does not attack
    }

    @Override
    public boolean upgrade() {
        return false;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }
}
