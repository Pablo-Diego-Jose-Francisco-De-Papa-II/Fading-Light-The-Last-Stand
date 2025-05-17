package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Ballista extends Building {

    private static BufferedImage ballistaImage;

    static {
        try {
            ballistaImage = ImageIO.read(new File("resources/buildings/ballista.png"));
        } catch (IOException e) {
            System.err.println("Failed to load ballista image.");
            ballistaImage = null;
        }
    }

    public Ballista(PlayingArea map, int x, int y) {
        super("Ballista", map, x, y,
                8,
                150,
                3,
                100,
                20,
                20,
                0.25f,
                ballistaImage
        );
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (isDestroyed() || !canAttack()) {
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
