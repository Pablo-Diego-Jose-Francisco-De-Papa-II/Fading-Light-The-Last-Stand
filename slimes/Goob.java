package slimes;

import game.PlayingArea;

public class Goob extends Slime {

    public Goob(PlayingArea map, int x, int y) {
        super(map, x, y,
                100,
                32,
                1,
                1000,
                1,
                30,
                "resources/floor.png");
    }

}
