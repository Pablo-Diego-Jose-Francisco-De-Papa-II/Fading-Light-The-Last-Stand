package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Trieda HellstormTurret reprezentuje obrannú budovu - Hellstorm turret,
 * ktorá slúži ako obranná budova pre nepriateľov a útoči na nich.
 */
public class HellstormTurret extends Building {

    private BufferedImage hellstormImage;
    private int level;

    /**
     * Vytvorí novú inštanciu Hellstorm turretu na určených súradniciach mapy.
     *
     * @param map referencie na hraciu plochu, kde je umiestnená
     * @param x súradnica X
     * @param y súradnica Y
     */
    public HellstormTurret(PlayingArea map, int x, int y) {
        super("Hellstorm Turret", map, x, y,
                8,
                400,
                2,
                250,
                15,
                20,
                3.0f,
                null
        );
        this.level = 1;
        this.loadImage();
        setImage(this.hellstormImage);
    }

    /**
     * Načíta obrázok pre aktuálnu úroveň z disku.
     */
    private void loadImage() {
        try {
            this.hellstormImage = ImageIO.read(new File("resources/hel" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.hellstormImage = null;
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

        if (this.canAttack()) {
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
        if (getLevel() >= 2) {
            System.out.println(getName() + " is already at max level!");
            return false;
        }

        if (getLevel() == 1) {
            setMaxHealth(getMaxHealth() + 50);
            setHealth(getMaxHealth());
            setDamage(getDamage() + 10);
        }

        setLevel(getLevel() + 1);
        this.level = getLevel();

        this.loadImage();
        setImage(this.hellstormImage);

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
        return (getLevel() == 1) ? 800 : 0;
    }

}
