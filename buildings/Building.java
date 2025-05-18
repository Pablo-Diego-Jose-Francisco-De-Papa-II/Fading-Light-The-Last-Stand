package buildings;

import game.PlayingArea;
import slimes.Slime;

import java.awt.image.BufferedImage;
import java.util.List;


/**
 * Abstraktná trieda reprezentujúca obrannú budovu v hre.
 * Obsahuje spoločné vlastnosti a metódy pre všetky typy budov.
 */
public abstract class Building {
    private final String name;
    private final int x;
    private final int y;
    private final int size;

    private int level;
    private int maxLevel;

    private int cost;
    private int maxHealth;
    private int health;

    private int damage;
    private final int range;
    private final float attackSpeed;
    private long lastAttackTime;

    private boolean destroyed;

    private final PlayingArea map;

    private BufferedImage image;

    /**
     * Konštruktor budovy.
     *
     * @param name         názov
     * @param map          herná mapa, na ktorej sa budova nachádza
     * @param x            X súradnica
     * @param y            Y súradnica
     * @param size         veľkosť
     * @param cost         cena
     * @param maxLevel     maximálna úroveň
     * @param maxHealth    maximálne zdravie
     * @param damage       počiatočné poškodenie
     * @param range        dosah útoku
     * @param attackSpeed  rýchlosť útoku
     * @param image        obrázok
     */
    public Building(String name, PlayingArea map, int x, int y, int size,
                    int cost, int maxLevel, int maxHealth, int damage, int range,
                    float attackSpeed, BufferedImage image) {
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

    /**
     * Aplikuje budove poškodenie.
     * Ak zdravie klesne na 0 alebo menej, budova sa označí ako zničená
     * a odstráni sa z hernej mapy.
     *
     * @param amount množstvo spôsobeného poškodenia
     */
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

    /**
     * @return true, ak je budova zničená
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }

    /**
     * Skontroluje, či budova môže aktuálne útočiť na základe cooldownu.
     *
     * @return false, ak môže útočiť, true inak
     */
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - this.lastAttackTime >= (1000 / this.attackSpeed)) {
            this.lastAttackTime = currentTime;
            return false;
        }
        return true;
    }

    /**
     *
     */
    public void updateCooldown() {
        this.lastAttackTime = System.currentTimeMillis();
    }

    /**
     * Vráti meno budovy.
     *
     * @return meno budovy
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vráti pozíciu X.
     *
     * @return pozíciu X
     */
    public int getX() {
        return this.x;
    }

    /**
     * Vráti cenu vylepšenia budovy.
     *
     * @return cenu vylepšenia budovy
     */
    public int getUpgradeCost() {
        return this.cost;
    }

    /**
     * Vráti pozíciu Y.
     *
     * @return pozíciu Y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Vráti veľkosť budovy.
     * @return veľkosť budovy
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Vráti aktuálnu úroveň budovy.
     *
     * @return aktuálnu úroveň budovy.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Vráti aktuálne životy budovy.
     *
     * @return aktuálne životy budovy
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Vráti maximálne životy budovy.
     *
     * @return maximálne životy budovy
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Vráti hodnotu poškodenia budovy.
     *
     * @return hodnota poškodenia budovy
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Vrátia počet súradnic, ktoré budovať musí budovať budovu, aby sa mohla upraviť.
     *
     * @return počet vzdialenosť ako ďaleko budova dostrelí
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Vrátia obrázok budovy.
     *
     * @return obrázok budovy
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Vytvorí inštanciu konkrétnej budovy na základe jej typu.
     *
     * @param type typ budovy
     * @param map  herná mapa
     * @param x    X súradnica
     * @param y    Y súradnica
     * @return nová budova, alebo null, ak typ nie je podporovaný
     */
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

    /**
     * Nastaví úroveň budovy.
     *
     * @param level nový level budovy
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Nastaví životy budovy.
     *
     * @param health nové životy
     */
    public void setHealth(int health) {
        this.health = Math.min(health, this.maxHealth);
    }

    /**
     * Nastaví maximálne životy budovy.
     *
     * @param maxHealth nové maximálne životy
     */
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = (int) maxHealth;
    }

    /**
     * Nastaví hodnotu poškodenia budovy.
     *
     * @param damage nové hodnoty poškodenia
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Odstráni budovu z hernej mapy (volané napr. pri predaji).
     */
    public void removeBuidingFromMap() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.map.getTile(this.x + i, this.y + j).removeBuilding();
            }
        }
    }

    /**
     * Nastaví obrázok budovy.
     *
     * @param image obrázok budovy
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Útočí na zoznam nepriateľov.
     * Implementácia závisí od konkrétnej podtriedy.
     *
     * @param slimes zoznam nepriateľov
     */
    public abstract void attack(List<Slime> slimes);

    /**
     * Pokúsi sa vylepšiť budovu.
     * Implementácia závisí od konkrétnej podtriedy.
     *
     * @return true, ak sa upgrade podaril, false inak
     */
    public abstract boolean upgrade();

}
