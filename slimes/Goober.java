package slimes;

import game.PlayingArea;

import java.awt.*;

/**
 * Trieda reprezentujúca konkrétny typ slima s názvom Goober.
 */
public class Goober extends Slime {

    /**
     * Vytvorí novú inštanciu Goober slima na zadaných súradniciach.
     *
     * @param map referencia na hernú mapu
     * @param x   X súradnica
     * @param y   Y súradnica
     */
    public Goober(PlayingArea map, int x, int y) {
        super(map, x, y,
                15,
                10,
                1,
                1000,
                1,
                10,
                Color.BLUE
        );
    }

}

