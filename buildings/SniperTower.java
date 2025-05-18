package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Trieda SniperTower reprezentuje obrannú budovu - Sniper tower,
 * ktorá slúži ako obranná budova pre nepriateľov a útoči na nich.
 */
public class SniperTower extends Building {

    private BufferedImage sniperTowerImage;
    private int level;

    /**
     * Vytvorí novú inštanciu SniperTower turretu na určených súradniciach mapy.
     *
     * @param map referencie na hraciu plochu, kde je umiestnená
     * @param x súradnica X
     * @param y súradnica Y
     */
    public SniperTower(PlayingArea map, int x, int y) {
        super("Sniper Tower", map, x, y,
                6,
                250,
                3,
                150,
                20,
                80,
                1,
                null
        );
        this.level = 1;
        this.loadImage();
        setImage(this.sniperTowerImage);
    }

    /**
     * Načíta obrázok pre aktuálnu úroveň z disku.
     */
    private void loadImage() {
        try {
            this.sniperTowerImage = ImageIO.read(new File("resources/sni" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.sniperTowerImage = null;
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
            if (this.isInRange(slime)) {
                slime.takeDamage(this.getDamage());
                break;
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
     * Vylepšenie na vyššiu úroveň, ak ešte nedosiahla maximum.
     * Zvyšuje životy a poškodenie.
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
                setDamage(getDamage() + 20);
            }
            case 2 -> {
                setMaxHealth(getMaxHealth() + 100);
                setHealth(getMaxHealth());
                setDamage(getDamage() + 30);
            }
        }

        setLevel(getLevel() + 1);
        this.level = getLevel();

        this.loadImage();
        setImage(this.sniperTowerImage);

        System.out.println(getName() + " upgraded to level " + getLevel());
        return true;
    }

    /**
     * Získa cenu za vylepšenie podľa jej aktuálnej úrovne.
     *
     * @return cena za upgrade, alebo 0, ak je už na maximálnej úrovni
     */
    @Override
    public int getUpgradeCost() {
        return switch (getLevel()) {
            case 1 -> 500;
            case 2 -> 750;
            default -> 0;
        };
    }

}
