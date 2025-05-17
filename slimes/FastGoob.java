package slimes;

import game.PlayingArea;

public class FastGoob extends Slime {
    public FastGoob(PlayingArea map, int startX, int startY) {
        super(map, startX, startY,100, 1,1,10,1,1,"resources/zombies/biter.png");
    }

    @Override
    public void move() {

    }

    @Override
    public void dealDamage() {

    }

    @Override
    public void die() {

    }
}
