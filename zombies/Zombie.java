package zombies;

import game.PlayingArea;
import game.Tile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

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

    // Move the zombie (you can modify this logic as needed)
    public void move() {
        if (this.x > 0 && this.map.getTile(this.x - 1, this.y).isWalkable()) {
            this.x--;
        }
    }

    // Update the zombie's state (called every frame)
    public void update() {
        // Move the zombie
        move();
        // Perform damage if needed
        dealDamage();
    }

    // Deal damage to a building if present
    public void dealDamage() {
        Tile tile = this.map.getTile(this.x, this.y);
        if (tile.getBuilding() != null) {
            tile.getBuilding().takeDamage(this.attackDamage);
        }
    }

    // Handle the zombie's death
    public void die() {
        System.out.println("Zombie at " + x + "," + y + " died.");
        // You can add additional cleanup logic here, like removing the zombie from the map or a list
    }

    // Method for zombies to take damage
    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            die();  // If health reaches 0, zombie dies
        }
    }

    // Draw the zombie on the screen
    public void draw(Graphics g) {
        // Here we are just using an icon (you can replace this with your own logic)
        Image image = Toolkit.getDefaultToolkit().getImage(this.icon); // Load the image based on the icon
        g.drawImage(image, x, y, size, size, null);  // Draw the zombie as an image
    }

    // Getters
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
