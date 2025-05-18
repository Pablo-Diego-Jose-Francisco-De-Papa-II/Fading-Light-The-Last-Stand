package slimes;

import game.PlayingArea;

import java.awt.*;

/**
 * Trieda reprezentujúca konkrétny typ slima s názvom Goob.
 */
public class Goob extends Slime {

    /**
     * Vytvorí novú inštanciu Goob slima na zadaných súradniciach.
     *
     * @param map referencia na hernú mapu
     * @param x   X súradnica
     * @param y   Y súradnica
     */
    public Goob(PlayingArea map, int x, int y) {
        super(map, x, y,
                10,
                5,
                1,
                100,
                1,
                10,
                Color.GREEN
        );
    }

}
