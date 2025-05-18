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
                10,
                0,
                1,
                1000,
                0,
                0,
                0.0f,
                townHallImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
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
