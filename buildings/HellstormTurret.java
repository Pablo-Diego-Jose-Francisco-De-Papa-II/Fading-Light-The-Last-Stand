package buildings;

import game.PlayingArea;
import slimes.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HellstormTurret extends Building {

    private static BufferedImage hellstormImage;

    static {
        try {
            hellstormImage = ImageIO.read(new File("resources/buildings/hellstorm.png"));
        } catch (IOException e) {
            System.err.println("Failed to load hellstorm turret image.");
            hellstormImage = null;
        }
    }

    public HellstormTurret(PlayingArea map, int x, int y) {
        super("Hellstorm Turret", map, x, y,
                4,
                400,
                2,
                250,
                15,
                20,
                3.0f,
                hellstormImage);
    }

    @Override
    public void attack(List<Slime> slimes) {
        if (isDestroyed()) {
            return;
        }

        if (!this.canAttack()) {
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

        System.out.println(getName() + " upgraded to level " + getLevel());
        return true;
    }

    @Override
    public int getUpgradeCost() {
        return (getLevel() == 1) ? 800 : 0;
    }

}
