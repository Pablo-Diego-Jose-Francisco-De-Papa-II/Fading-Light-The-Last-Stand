package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SniperTower extends Building {

    private BufferedImage sniperTowerImage;
    private int level;

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

    private void loadImage() {
        try {
            this.sniperTowerImage = ImageIO.read(new File("resources/sni" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.sniperTowerImage = null;
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
            if (this.isInRange(slime)) {
                slime.takeDamage(this.getDamage());
                break;
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

    @Override
    public int getUpgradeCost() {
        return switch (getLevel()) {
            case 1 -> 500;
            case 2 -> 750;
            default -> 0;
        };
    }

}
