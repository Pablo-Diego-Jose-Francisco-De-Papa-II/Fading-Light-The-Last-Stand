package slimes;

import buildings.Building;
import game.PlayingArea;
import game.Tile;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Slime {

    private int health;
    private int size;
    private int speed;
    private int attackDamage;
    private int attackRange;
    private int attackSpeed;
    private int attackCooldown = 0;
    private String icon;

    private PlayingArea map;
    private int x;
    private int y;

    private BufferedImage image;

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

        try {
            this.image = ImageIO.read(new File(icon));
        } catch (IOException e) {
            e.printStackTrace();
            this.image = null;
        }
    }

    public void update() {
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        int[] target = this.map.findNearestBuilding(this.x, this.y);
        if (target == null) {
            return;
        }

        int targetX = target[0];
        int targetY = target[1];

        int distance = Math.abs(this.x - targetX) + Math.abs(this.y - targetY);

        if (distance <= this.attackRange) {
            this.dealDamage(targetX, targetY);
        } else {
            this.moveTowards(targetX, targetY);
        }
    }

    public void moveTowards(int targetX, int targetY) {
        int dx = Integer.compare(targetX, this.x);
        int dy = Integer.compare(targetY, this.y);

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (this.map.isValidCoordinate(newX, newY)) {
            Tile nextTile = this.map.getTile(newX, newY);
            if (nextTile != null && nextTile.isWalkable()) {
                this.x = newX;
                this.y = newY;
            }
        }
    }

    public void dealDamage(int targetX, int targetY) {
        if (this.attackCooldown > 0) {
            return;
        }

        Tile tile = this.map.getTile(targetX, targetY);
        if (tile != null) {
            Building building = tile.getBuilding();
            if (building != null) {
                building.takeDamage(this.attackDamage);
                this.attackCooldown = this.attackSpeed;

                if (building.isDestroyed()) {
                    tile.removeBuilding();
                }
            }
        }
    }

    public void takeDamage(int amount) {
        this.health -= amount;
    }

    public void draw(Graphics g, int tileSize) {
        if (this.image != null) {
            int pixelX = this.x * tileSize;
            int pixelY = this.y * tileSize;
            g.drawImage(this.image, pixelX, pixelY, tileSize, tileSize, null);
        } else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(this.x * tileSize, this.y * tileSize, tileSize, tileSize);
        }
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

    public boolean isDead() {
        return this.health <= 0;
    }

}
