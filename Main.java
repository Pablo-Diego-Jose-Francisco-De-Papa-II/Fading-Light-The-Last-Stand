import buildings.WatchTower;
import game.Game;
import game.PlayingArea;
import game.BuildingManager;
import slimes.Goob;
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
            Goob goob1 = new Goob(map, 40, 50);
            Goob goob2 = new Goob(map, 60, 55);
            Goob goob3 = new Goob(map, 100, 100);

            manager.addSlime(goob1);
            manager.addSlime(goob2);
            manager.addSlime(goob3);

            // Tower should already be in manager via placeBuilding(), but add just in case
            manager.addBuilding(tower);

            manager.update(); // Buildings attack slimes

            System.out.println("Biter1 health: " + goob1.getHealth());
            System.out.println("Biter2 health: " + goob2.getHealth());
            System.out.println("Biter3 health: " + goob3.getHealth());

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
