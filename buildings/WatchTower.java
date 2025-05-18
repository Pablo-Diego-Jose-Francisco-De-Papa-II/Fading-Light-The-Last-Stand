package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Trieda WatchTower reprezentuje obrannú budovu – Watch tower,
 * ktorá útočí na nepriateľov v určitom dosahu.
 */
public class WatchTower extends Building {

    private BufferedImage image;
    private int level;

    /**
     * Vytvorí novú WatchTower na zadaných súradniciach na hracej ploche.
     *
     * @param map referencia na hraciu plochu
     * @param x   súradnica X
     * @param y   súradnica Y
     */
    public WatchTower(PlayingArea map, int x, int y) {
        super("Watch Tower", map, x, y,
                6,
                100,
                3,
                100,
                10,
                40,
                1f,
                null
        );
        this.level = 1;
        this.loadImage();
        setImage(this.image);
    }

    /**
     * Načíta obrázok pre aktuálnu úroveň veže z disku.
     */
    private void loadImage() {
        try {
            this.image = ImageIO.read(new File("resources/wt" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.image = null;
        }
    }

    /**
     * Útok na nepriateľov v dosahu. Vyberie prvého živého nepriateľa a spôsobí mu poškodenie.
     * Metóda nič nevykoná, ak je veža zničená alebo čaká na cooldown.
     *
     * @param slimes zoznam nepriateľov, na ktorých sa môže útočiť
     */
    @Override
    public void attack(List<Slime> slimes) {
        if (isDestroyed()) {
            return;
        }

        if (canAttack()) {
            return;
        }

        for (Slime slime : slimes) {
            if (!slime.isDead()) {
                boolean inRange = this.isInRange(slime);
                if (inRange) {
                    slime.takeDamage(getDamage());
                    updateCooldown();
                    break;
                }
            }
        }
    }

    /**
     * Overí, či je daný nepriateľ v dosahu.
     *
     * @param slime nepriateľ, ktorého pozícia sa overuje
     * @return true, ak je nepriateľ v dosahu, inak false
     */
    private boolean isInRange(Slime slime) {
        int zx = slime.getX();
        int zy = slime.getY();

        int centerX = this.getX() + getSize() / 2;
        int centerY = this.getY() + getSize() / 2;

        int dx = zx - centerX;
        int dy = zy - centerY;
        return (dx * dx + dy * dy) <= (getRange() * getRange());
    }

    /**
     * Vylepší vežu na vyššiu úroveň, ak ešte nedosiahla maximum.
     *
     * @return true, ak bolo vylepšenie úspešné, inak false
     */
    @Override
    public boolean upgrade() {
        if (getLevel() >= 3) {
            System.out.println(getName() + " is already at max level!");
            return false;
        }

        switch (getLevel()) {
            case 1 -> {
                setMaxHealth(getMaxHealth() + 50);
                setHealth(getMaxHealth());
                setDamage(getDamage() + 15);
            }
            case 2 -> {
                setMaxHealth(getMaxHealth() + 75);
                setHealth(getMaxHealth());
                setDamage(getDamage() + 25);
            }
        }

        this.setLevel(getLevel() + 1);
        this.level = getLevel();

        this.loadImage();
        setImage(this.image);

        System.out.println(getName() + " upgraded to level " + getLevel());
        return true;
    }


    /**
     * Získa cenu za vylepšenie veže podľa jej aktuálnej úrovne.
     *
     * @return cena za upgrade, alebo 0, ak je už na maximálnej úrovni
     */
    @Override
    public int getUpgradeCost() {
        return switch (getLevel()) {
            case 1 -> 200;
            case 2 -> 300;
            default -> 0;
        };
    }

}
