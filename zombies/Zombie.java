package zombies;

import game.PlayingArea;
import game.Tile;

public abstract class Zombie {

    private int health;
    private int size;
    private int speed;
    private int attackDamage;
    private int attackRange;
    private int attackSpeed;
    private String icon;

    private PlayingArea map;
    private int x;
    private int y;

    public Zombie(PlayingArea map, int startX, int startY, int health, int size, int speed, int attackDamage, int attackRange, int attackSpeed, String icon) {
        this.map = map;
        this.x = startX;
        this.y = startY;
        this.health = health;
        this.size = size;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.attackSpeed = attackSpeed;
        this.icon = icon;
    }

    public void move() {
        if (this.x > 0 && this.map.getTile(this.x - 1, this.y).isWalkable()) {
            this.x--;
        }
    }

    public void dealDamage() {
        Tile tile = this.map.getTile(this.x, this.y);
        if (tile.getBuilding() != null) {
            tile.getBuilding().takeDamage(this.attackDamage);
        }
    }

    public void die() {
        System.out.println("Zombie at " + x + "," + y + " died.");
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHealth() {
        return this.health;
    }

    public String getIcon() {
        return this.icon;
    }
}
