//package buildings;
//
//import game.PlayingArea;
//import slimes.Slime;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class Flamethrower extends Building {
//
//    private static BufferedImage flamethrowerImage;
//
//    static {
//        try {
//            flamethrowerImage = ImageIO.read(new File("resources/buildings/flamethrower.png"));
//        } catch (IOException e) {
//            System.err.println("Failed to load flamethrower image.");
//            flamethrowerImage = null;
//        }
//    }
//
//    public Flamethrower(PlayingArea map, int x, int y) {
//        super("Flamethrower", map, x, y, 3, 110, 3, 130, 10, 70, 0.5f, flamethrowerImage);
//    }
//
//    @Override
//    public void attack(List<Slime> slimes) {
//        if (isDestroyed() || !canAttack()) return;
//
//        for (Slime slime : slimes) {
//            if (isInRange(slime)) {
//                slime.takeDamage(this.getDamage());
//            }
//        }
//    }
//
//    @Override
//    public boolean upgrade() {
//        return false;
//    }
//
//    @Override
//    public int getUpgradeCost() {
//        return 0;
//    }
//
//    private boolean isInRange(Slime slime) {
//        int dx = slime.getX() - (getX() + getSize() / 2);
//        int dy = slime.getY() - (getY() + getSize() / 2);
//        return dx * dx + dy * dy <= getRange() * getRange();
//    }
//}
