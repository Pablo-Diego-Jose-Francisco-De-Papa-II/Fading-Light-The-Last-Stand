package game;

import buildings.Building;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Trieda reprezentujúca jeden tile na hernej mape.
 * Dlaždica môže obsahovať budovu, môže byť priechodná alebo nepriechodná
 * a má svoju vizuálnu reprezentáciu pomocou obrázku.
 */
public class Tile {

    private static final Random RANDOM = new Random();
    private static final BufferedImage DEFAULT_IMAGE = loadImage();

    private final int x;
    private final int y;
    private boolean isWalkable;
    private Building building;
    private final BufferedImage image;

    /**
     * Vytvorí novú dlaždicu na zadaných súradniciach, ktorá je štandardne priechodná
     * a neobsahuje žiadnu budovu. Obrázok sa náhodne vyberie zo základného obrázka.
     *
     * @param x X-ová súradnica dlaždice
     * @param y Y-ová súradnica dlaždice
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWalkable = true;
        this.building = null;
        this.image = getRandomSubImage();
    }

    /**
     * Vytvorí kópiu existujúcej dlaždice (klon).
     *
     * @param other dlaždica, z ktorej sa preberajú hodnoty
     */
    public Tile(Tile other) {
        this.x = other.x;
        this.y = other.y;
        this.isWalkable = other.isWalkable;
        this.image = other.image;
        this.building = other.building;
    }

    /**
     * Načíta základný obrázok podlahy z disku.
     *
     * @return načítaný obrázok
     */
    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(new File("resources/floor.png"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + "resources/floor.png", e);
        }
    }

    /**
     * Získa náhodný výrez zo základného obrázka podlahy.
     *
     * @return Náhodne orezaný obrázok
     */
    private static BufferedImage getRandomSubImage() {
        return Tile.DEFAULT_IMAGE.getSubimage(RANDOM.nextInt(41), RANDOM.nextInt(41), 10, 10);
    }

    /**
     * Zistí, či je dlaždica priechodná.
     *
     * @return true, ak je priechodná, inak false
     */
    public boolean isWalkable() {
        return this.isWalkable;
    }

    /**
     * Vráti budovu, ktorá sa nachádza na tejto dlaždici.
     *
     * @return Objekt Building
     */
    public Building getBuilding() {
        return this.building;
    }

    /**
     * Nastaví budovu na túto dlaždicu. Dlaždica sa stane nepriechodnou.
     *
     * @param building Budova, ktorú má dlaždica obsahovať
     */
    public void setBuilding(Building building) {
        this.building = building;

        if (this.building != null) {
            this.isWalkable = false;
        }
    }

    /**
     * Odstráni budovu z tejto dlaždice a nastaví ju ako priechodnú.
     */
    public void removeBuilding() {
        this.building = null;
        this.isWalkable = true;
    }

    /**
     * Vráti obrázok spojený s touto dlaždicou.
     *
     * @return BufferedImage reprezentujúci tile
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Zistí, či dlaždica obsahuje budovu.
     *
     * @return true, ak obsahuje budovu, inak false
     */
    public boolean hasBuilding() {
        return this.building != null;
    }

}