package slimes;

import game.PlayingArea;

import java.awt.*;

/**
 * Trieda reprezentujúca konkrétny typ slima s názvom Goobster.
 */
public class Goobster extends Slime {

    /**
     * Vytvorí novú inštanciu Goobster slima na zadaných súradniciach.
     *
     * @param map referencia na hernú mapu
     * @param x   X súradnica
     * @param y   Y súradnica
     */
    public Goobster(PlayingArea map, int x, int y) {
        super(map, x, y,
                50,
                25,
                1,
                0,
                1,
                10,
                Color.BLACK
        );
    }

}
