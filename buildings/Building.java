package buildings;

import game.PlayingArea;
import game.Tile;
import zombies.Zombie;

import java.util.List;

public abstract class Building {
    protected int health;
    protected int damage;
    protected int range;
    protected int level;
    protected int cost;
    protected int x;
    protected int y;
    protected boolean destroyed;
    protected int size; // Add size property

    protected PlayingArea map;

    public Building(PlayingArea map, int x, int y, int size) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.size = size; // Initialize size
        this.level = 1;
        this.destroyed = false;
    }

    public abstract void attack(List<Zombie> zombies);

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.destroyed = true;
            map.getTile(x, y).removeBuilding();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void upgrade() {
        level++;
        this.health += 50;
        this.damage += 10;
        this.range += 1;
    }

    public int getCost() {
        return cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    // Getter for size
    public int getSize() {
        return size;
    }

    public static Building createBuilding(String type, PlayingArea map, int x, int y) {
        return switch (type) {
            case "Watch Tower" -> new WatchTower(map, x, y);
            case "Ballista" -> new Ballista(map, x, y);
            case "Mortar" -> new Mortar(map, x, y);
            case "Sniper Tower" -> new SniperTower(map, x, y);
            case "Flamethrower" -> new Flamethrower(map, x, y);
            case "Hellstorm Turret" -> new HellstormTurret(map, x, y);
            case "Rocket Silo" -> new RocketSilo(map, x, y);
            default -> null;
        };
    }
}
