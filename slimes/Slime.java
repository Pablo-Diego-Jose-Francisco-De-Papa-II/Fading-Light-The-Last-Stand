package slimes;

import buildings.Building;
import game.PlayingArea;
import game.Tile;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Abstraktná trieda reprezentujúca základného slima v hre.
 * Slime sa pohybuje po mape, hľadá najbližšiu budovu a útočí na ňu.
 */
public abstract class Slime {

    private int health;
    private final int size;
    private final int speed;
    private final int attackDamage;
    private final int attackRange;
    private final int attackSpeed;
    private int attackCooldown = 0;

    private final PlayingArea map;
    private int x;
    private int y;
    private final Color color;

    /**
     * Konštruktor pre inicializáciu vlastností slima.
     *
     * @param map           referencia na hernú mapu
     * @param startX        X súradnica
     * @param startY        Y súradnica
     * @param health        životy
     * @param size          veľkosť slima
     * @param speed         rýchlosť slima
     * @param attackDamage  poškodenie
     * @param attackRange   dosah útoku
     * @param attackSpeed   rýchlosť útokov
     * @param color         farba slima
     */
    public Slime(PlayingArea map, int startX, int startY, int health, int size, int speed,
                 int attackDamage, int attackRange, int attackSpeed, Color color) {
        this.map = map;
        this.x = startX;
        this.y = startY;
        this.health = health;
        this.size = size;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.attackSpeed = attackSpeed;
        this.color = color;
    }

    /**
     * Aktualizuje stav slima. Hľadá najbližšiu budovu a ak je v dosahu, útočí na ňu.
     */
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

    /**
     * Pohne slima o jedno políčko smerom k cieľovej pozícii, ak je možné po nej kráčať.
     *
     * @param targetX X súradnica cieľa
     * @param targetY Y súradnica cieľa
     */
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

    /**
     * Útočí na budovu na cieľovej pozícii, ak je pripravený.
     *
     * @param targetX X súradnica cieľa
     * @param targetY Y súradnica cieľa
     */
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

    /**
     * Zníži život slima o dané množstvo.
     *
     * @param amount množstvo poškodenia
     */
    public void takeDamage(int amount) {
        this.health -= amount;
    }

    /**
     * Vykreslí slima na plátno.
     *
     * @param g        grafický kontext
     * @param tileSize veľkosť políčka
     */
    public void draw(Graphics g, int tileSize) {
        g.setColor(this.color);
        g.fillRect(this.x * tileSize, this.y * tileSize, tileSize, tileSize);
    }

    /**
     * @return aktuálna X pozícia
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return aktuálna Y pozícia slima
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return true, ak je slime mŕtvy
     */
    public boolean isDead() {
        return this.health <= 0;
    }

}
