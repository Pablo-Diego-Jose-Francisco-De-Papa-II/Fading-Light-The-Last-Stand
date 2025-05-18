package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Trieda Barricade reprezentuje obrannú budovu - barikádu,
 * ktorá slúži ako prekážka pre nepriateľov, ale sama neútočí.
 */
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

    /**
     * Vytvorí novú inštanciu barikády na určených súradniciach mapy.
     *
     * @param map referencie na hraciu plochu, kde je barikáda umiestnená
     * @param x súradnica X
     * @param y súradnica Y
     */
    public Barricade(PlayingArea map, int x, int y) {
        super("Barricade", map, x, y,
                1,
                10,
                1,
                200,
                0,
                0,
                0,
                barricadeImage
        );
    }

    /**
     * Barikáda nemá žiadnu útočnú funkciu.
     *
     * @param slimes zoznam nepriateľov v dosahu
     */
    @Override
    public void attack(List<Slime> slimes) {
    }

    /**
     * Barikádu nie je možné vylepšiť.
     *
     * @return false
     */
    @Override
    public boolean upgrade() {
        return false;
    }

    /**
     * Cena na vylepšenie barikády.
     *
     * @return 0
     */
    @Override
    public int getUpgradeCost() {
        return 0;
    }

}
