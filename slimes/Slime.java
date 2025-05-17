package slimes;

import game.PlayingArea;
import game.Tile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Slime {

    private int health;
    private int size;
    private int speed;
    private int attackDamage;
    private int attackRange;
    private int attackSpeed;
    private String icon;

    protected PlayingArea map;
    protected int x;
    protected int y;

    public Slime(PlayingArea map, int startX, int startY, int health, int size, int speed, int attackDamage, int attackRange, int attackSpeed, String icon) {
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
        // Pokús sa ísť doľava, ale najskôr over súradnice a walkability
        int nextX = this.x - 1;
        int nextY = this.y;

        if (nextX >= 0) { // overenie, či súradnica nie je záporná
            Tile nextTile = this.map.getTile(nextX, nextY);
            if (nextTile != null && nextTile.isWalkable()) {
                this.x = nextX;
            }
            // else: nemožno sa pohybovať, zostávaj na mieste
        }
    }

    public void update() {
        move();
        dealDamage();
    }

    public void dealDamage() {
        Tile tile = this.map.getTile(this.x, this.y);
        if (tile != null && tile.getBuilding() != null) {
            tile.getBuilding().takeDamage(this.attackDamage);
        }
    }

    public void die() {
        System.out.println("Slime at " + x + "," + y + " died.");
        // Odstránenie slima z manažéra alebo mapy by malo byť riešené mimo tejto triedy
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            die();
        }
    }

    public void draw(Graphics g) {
        Image image = Toolkit.getDefaultToolkit().getImage(this.icon);
        g.drawImage(image, x, y, size, size, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isDead() {
        return health <= 0;
    }
}
