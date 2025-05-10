package game;

import buildings.Building;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Tile {

    private static final Random random = new Random();
    private static final BufferedImage DEFAULT_IMAGE = loadImage();

    private final int x;
    private final int y;
    private boolean isWalkable;
    private Building building;
    private final BufferedImage image;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWalkable = true;
        this.building = null;
        this.image = getRandomSubImage();
    }

    public Tile(Tile other) {
        this.x = other.x;
        this.y = other.y;
        this.isWalkable = other.isWalkable;
        this.image = other.image;
        this.building = other.building;
    }


    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(new File("resources/floor.png"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + "resources/floor.png", e);
        }
    }

    private static BufferedImage getRandomSubImage() {
        return Tile.DEFAULT_IMAGE.getSubimage(random.nextInt(41), random.nextInt(41), 10, 10);
    }

    public int[] getPosition() {
        return new int[]{this.x, this.y};
    }

    public boolean isWalkable() {
        return this.isWalkable;
    }

    public void setWalkable(boolean walkable) {
        this.isWalkable = walkable;
    }

    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;

        if (this.building != null) {
            this.isWalkable = false;
        }
    }

    public void removeBuilding() {
        this.building = null;
        this.isWalkable = true;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void damageBuilding(int amount) {
        if (building != null) {
            building.takeDamage(amount);
            if (building.getHealth() <= 0) {
                removeBuilding();
            }
        }
    }

}