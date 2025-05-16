package buildings;

import game.PlayingArea;
import zombies.Zombie;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Building {
    protected String name;
    protected int x;
    protected int y;
    protected int size;

    protected int level;
    protected int maxLevel;

    protected int cost;
    protected int upgradeCost;

    protected int health;
    protected int maxHealth;

    protected int damage;
    protected int range;
    protected float attackSpeed; // attacks per second
    protected long lastAttackTime; // timestamp of last attack (ms)

    protected boolean destroyed;

    protected PlayingArea map;

    protected BufferedImage image;

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
        this.upgradeCost = cost;  // initial upgrade cost same as build cost

        this.maxHealth = maxHealth;
        this.health = maxHealth;

        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;

        this.lastAttackTime = 0;
        this.destroyed = false;

        this.image = image;
    }

    public abstract void attack(List<Zombie> zombies);

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.destroyed = true;
            // Free tiles when destroyed
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    map.getTile(x + i, y + j).removeBuilding();
                }
            }
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    // Upgrade increases stats and costs, max level check
    public boolean upgrade() {
        if (level >= maxLevel) {
            System.out.println(name + " is already at max level.");
            return false;
        }
        level++;

        // Example upgrade logic — tweak as needed
        maxHealth += 50;
        health = maxHealth;  // restore health on upgrade
        damage += 10;
        range += 1;
        attackSpeed += 0.1f;  // faster shooting
        upgradeCost += cost / 2;  // cost increases each upgrade

        System.out.println(name + " upgraded to level " + level);
        return true;
    }

    // Attack cooldown check
    protected boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= (1000 / attackSpeed)) {
            lastAttackTime = currentTime;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public int getCost() {
        return cost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public BufferedImage getImage() {
        return image;
    }

    public static Building createBuilding(String type, PlayingArea map, int x, int y) {
        return switch (type) {
            case "Watch Tower" -> new WatchTower(map, x, y);
//            case "Ballista" -> new Ballista(map, x, y);
//            case "Mortar" -> new Mortar(map, x, y);
//            case "Sniper Tower" -> new SniperTower(map, x, y);
//            case "Flamethrower" -> new Flamethrower(map, x, y);
//            case "Hellstorm Turret" -> new HellstormTurret(map, x, y);
//            case "Rocket Silo" -> new RocketSilo(map, x, y);
            default -> null;
        };
    }

}
