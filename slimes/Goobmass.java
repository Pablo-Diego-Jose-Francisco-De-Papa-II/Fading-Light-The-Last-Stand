package slimes;

import game.PlayingArea;

import java.awt.*;

/**
 * Trieda reprezentujúca konkrétny typ slima s názvom Goobmass.
 */
public class Goobmass extends Slime {

    /**
     * Vytvorí novú inštanciu Goobmass slima na zadaných súradniciach.
     *
     * @param map referencia na hernú mapu
     * @param x   X súradnica
     * @param y   Y súradnica
     */
    public Goobmass(PlayingArea map, int x, int y) {
        super(map, x, y,
                20,
                20,
                1,
                0,
                1,
                10,
                Color.yellow
        );
    }

}
