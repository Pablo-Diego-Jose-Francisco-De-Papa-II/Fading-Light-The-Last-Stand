package slimes;

import buildings.Building;
import game.PlayingArea;
import game.Tile;

import java.awt.*;

public abstract class Slime {

    private int health;
    private int size;
    private int speed;
    private int attackDamage;
    private int attackRange;
    private int attackSpeed;  // interval útoku v tickoch
    private int attackCooldown = 0; // odpočítavanie do ďalšieho útoku
    private String icon;

    private PlayingArea map;
    private int x;
    private int y;

    public Slime(PlayingArea map, int startX, int startY, int health, int size, int speed,
                 int attackDamage, int attackRange, int attackSpeed, String icon) {
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

    public void update() {
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        int[] target = map.findNearestBuilding(x, y);
        if (target == null) return;

        int targetX = target[0];
        int targetY = target[1];

        int distance = Math.abs(x - targetX) + Math.abs(y - targetY);

        if (distance <= attackRange) {
            dealDamage(targetX, targetY);  // Tu posielame pozíciu budovy, aby sme na ňu útočili
        } else {
            moveTowards(targetX, targetY);
        }
    }


    public void moveTowards(int targetX, int targetY) {
        int dx = Integer.compare(targetX, x);
        int dy = Integer.compare(targetY, y);

        int newX = x + dx;
        int newY = y + dy;

        if (map.isValidCoordinate(newX, newY)) {
            Tile nextTile = map.getTile(newX, newY);
            if (nextTile != null && nextTile.isWalkable()) {
                this.x = newX;
                this.y = newY;
            }
        }
    }

    public void dealDamage(int targetX, int targetY) {
        if (attackCooldown > 0) return;

        Tile tile = this.map.getTile(targetX, targetY);
        if (tile != null) {
            Building building = tile.getBuilding();
            if (building != null) {
                building.takeDamage(this.attackDamage);
                attackCooldown = attackSpeed;

                if (building.isDestroyed()) {
                    tile.removeBuilding();
                }
            }
        }
    }



    public void die() {
        System.out.println("Slime at " + x + "," + y + " died.");
        // Tu by si mal informovať správcu slimákov, že zomrel (napr. odstrániť z listu)
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            die();
        }
    }

    public void draw(Graphics g, int tileSize) {
        Image image = Toolkit.getDefaultToolkit().getImage(this.icon);
        int pixelX = x * tileSize;
        int pixelY = y * tileSize;
        g.drawImage(image, pixelX, pixelY, tileSize, tileSize, null);
    }


    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public boolean isDead() { return health <= 0; }

    public String getIcon() { return icon; }
}
