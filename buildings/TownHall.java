package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Trieda TownHall reprezentuje hlavnú budovu - TownHall,
 * ktorá slúži ako ciel pre nepriateľov.
 */
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

    /**
     * Vytvorí inštanciu TownHall na určených súradniciach mapy.
     *
     * @param map referencie na hraciu plochu, kde je TownHall umiestnená
     * @param x súradnica X
     * @param y súradnica Y
     */
    public TownHall(PlayingArea map, int x, int y) {
        super("Town Hall", map, x, y,
                10,
                0,
                1,
                1000,
                0,
                0,
                0,
                townHallImage);
    }

    /**
     * Nemá žiadnu útočnú funkciu.
     *
     * @param slimes zoznam nepriateľov v dosahu
     */
    @Override
    public void attack(List<Slime> slimes) {
        // Town hall neútočí
    }

    /**
     * Town hall nie je možné vylepšiť.
     *
     * @return false
     */
    @Override
    public boolean upgrade() {
        return false;
    }

    /**
     * Náklady na vylepšenie sú vždy nulové, pretože sa nedá vylepšiť.
     *
     * @return 0
     */
    @Override
    public int getUpgradeCost() {
        return 0;
    }

}
