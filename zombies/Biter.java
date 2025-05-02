package zombies;

import game.PlayingArea;

public class Biter extends Zombie {

    public Biter(PlayingArea map, int startX, int startY) {
        super(map, startX, startY,100, 1,1,10,1,1,"resources/zombies/biter.png");
    }

}
