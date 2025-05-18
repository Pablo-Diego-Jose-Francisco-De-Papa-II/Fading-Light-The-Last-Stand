package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Barricade extends Building {

    private static BufferedImage barricadeImage;

    static {
        try {
            barricadeImage = ImageIO.read(new File("resources/bar.png"));
        } catch (IOException e) {
            System.err.println("Failed to load barricade image.");
            barricadeImage = null;
        }
    }

    public Barricade(PlayingArea map, int x, int y) {
        super("Barricade", map, x, y,
                1,
                5,
                1,
                100,
                0,
                0,
                0,
                barricadeImage
        );
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
