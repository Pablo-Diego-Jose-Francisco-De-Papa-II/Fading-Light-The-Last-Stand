package slimes;

import game.PlayingArea;

public class Goob extends Slime {

    public Goob(PlayingArea map, int x, int y) {
        super(map, x, y,
                1,
                32,
                1,
                0,
                1,
                30,
                "resources/floor.png");
    }
}
