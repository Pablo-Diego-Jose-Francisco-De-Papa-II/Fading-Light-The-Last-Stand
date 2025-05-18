package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Ballista extends Building {

    private BufferedImage ballistaImage;
    private int level;

    public Ballista(PlayingArea map, int x, int y) {
        super("Ballista", map, x, y,
                8,
                150,
                3,
                100,
                50,
                20,
                0.25f,
                null
        );
        this.level = 1;
        this.loadImage();
        setImage(this.ballistaImage);
    }

    private void loadImage() {
        try {
            this.ballistaImage = ImageIO.read(new File("resources/ba" + this.level + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load watchtower image for level " + this.level);
            this.ballistaImage = null;
        }
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (isDestroyed() || canAttack()) {
            return;
        }

        for (Slime slime : slimes) {
            if (this.isInRange(slime)) {
                slime.takeDamage(getDamage());
                break;
            }
        }
    }

    private boolean isInRange(Slime slime) {
        int centerX = getX() + getSize() / 2;
        int centerY = getY() + getSize() / 2;

        int dx = slime.getX() - centerX;
        int dy = slime.getY() - centerY;

        return dx * dx + dy * dy <= getRange() * getRange();
    }

    @Override
    public boolean upgrade() {
        if (getLevel() >= 3) {
            System.out.println(getName() + " is already at max level!");
            return false;
        }

        switch (getLevel()) {
            case 1 -> {
                setMaxHealth(getMaxHealth() + 25);
                setHealth(getMaxHealth());
                setDamage(getDamage() + 10);
            }
            case 2 -> {
                setMaxHealth(getMaxHealth() + 50);
                setHealth(getMaxHealth());
                setDamage(getDamage() + 10);
            }
        }

        setLevel(getLevel() + 1);
        this.level = getLevel();

        this.loadImage();
        setImage(this.ballistaImage);

        System.out.println(getName() + " upgraded to level " + getLevel());
        return true;
    }

    @Override
    public int getUpgradeCost() {
        return switch (getLevel()) {
            case 1 -> 300;
            case 2 -> 450;
            default -> 0;
        };
    }

}
