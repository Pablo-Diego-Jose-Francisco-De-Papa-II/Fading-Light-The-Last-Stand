package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class WatchTower extends Building {

    private BufferedImage image;
    private int level;

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

    private void loadImage() {
        try {
            this.image = ImageIO.read(new File("resources/wt" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.image = null;
        }
    }

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

    private boolean isInRange(Slime slime) {
        int zx = slime.getX();
        int zy = slime.getY();

        int centerX = this.getX() + getSize() / 2;
        int centerY = this.getY() + getSize() / 2;

        int dx = zx - centerX;
        int dy = zy - centerY;
        return (dx * dx + dy * dy) <= (getRange() * getRange());
    }

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

    @Override
    public int getUpgradeCost() {
        return switch (getLevel()) {
            case 1 -> 200;
            case 2 -> 300;
            default -> 0;
        };
    }

}
