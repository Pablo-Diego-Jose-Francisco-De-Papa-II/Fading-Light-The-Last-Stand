package buildings;

import game.PlayingArea;
import slimes.Slime;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Building {
    private String name;
    private int x;
    private int y;
    private int size;

    private int level;
    private int maxLevel;

    private int cost; // build cost
    private int maxHealth;
    private int health;

    private int damage;
    private int range;
    private float attackSpeed;
    private long lastAttackTime;

    private boolean destroyed;

    private PlayingArea map;

    private BufferedImage image;

    public Building(String name, PlayingArea map, int x, int y, int size,
                    int cost, int maxLevel, int maxHealth,
                    int damage, int range, float attackSpeed, BufferedImage image) {
        this.name = name;
        this.map = map;
        this.x = x;
        this.y = y;
        this.size = size;

        this.level = 1;
        this.maxLevel = maxLevel;

        this.cost = cost;
        this.maxHealth = maxHealth;
        this.health = maxHealth;

        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;

        this.lastAttackTime = 0;
        this.destroyed = false;

        this.image = image;
    }

    public abstract void attack(List<Slime> slimes);

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.destroyed = true;
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    this.map.getTile(this.x + i, this.y + j).removeBuilding();
                }
            }
        }
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public abstract boolean upgrade();

    public abstract int getUpgradeCost();

    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - this.lastAttackTime >= (1000 / this.attackSpeed)) {
            this.lastAttackTime = currentTime;
            return false;
        }
        return true;
    }

    public void updateCooldown() {
        this.lastAttackTime = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public static Building createBuilding(String type, PlayingArea map, int x, int y) {
        return switch (type) {
            case "Watch Tower" -> new WatchTower(map, x, y);
            case "Ballista" -> new Ballista(map, x, y);
            //case "Mortar" -> new Mortar(map, x, y);
            case "Sniper Tower" -> new SniperTower(map, x, y);
            //case "Flamethrower" -> new Flamethrower(map, x, y);
            case "Hellstorm Turret" -> new HellstormTurret(map, x, y);
            case "Barricade" -> new Barricade(map, x, y);
            default -> null;
        };
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, this.maxHealth);
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void removeYourself() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.map.getTile(this.x + i, this.y + j).removeBuilding();
            }
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
