import buildings.WatchTower;
import game.Game;
import game.PlayingArea;
import game.BuildingManager;
import slimes.Biter;
import ui.BuildHUD;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();

            PlayingArea map = game.getPlayingArea();

            // Create and place WatchTower
            WatchTower tower = new WatchTower(map, 50, 50);
            boolean placed = map.placeBuilding(tower);
            if (placed) {
                System.out.println("WatchTower successfully placed at (50, 50)");
            } else {
                System.out.println("Failed to place WatchTower at (50, 50)");
            }

            BuildingManager manager = game.getBuildingManager();

            // Create Biters (assuming Biter extends Slime and constructor exists)
            Biter biter1 = new Biter(map, 40, 50);
            Biter biter2 = new Biter(map, 60, 55);
            Biter biter3 = new Biter(map, 100, 100);

            manager.addSlime(biter1);
            manager.addSlime(biter2);
            manager.addSlime(biter3);

            // Tower should already be in manager via placeBuilding(), but add just in case
            manager.addBuilding(tower);

            manager.update(); // Buildings attack slimes

            System.out.println("Biter1 health: " + biter1.getHealth());
            System.out.println("Biter2 health: " + biter2.getHealth());
            System.out.println("Biter3 health: " + biter3.getHealth());

            // Refresh UI
            game.getBuildHUD().repaint();

            BuildHUD buildHUD = game.getBuildHUD();

            buildHUD.getShopButton().addActionListener(e -> {
                // Your shop code here
            });

            buildHUD.getStartWaveButton().addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Wave started!");
                game.switchHUD("wave");
            });
        });
    }
}
