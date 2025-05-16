import buildings.WatchTower;
import game.Game;
import game.PlayingArea;
import game.Shop;
import ui.BuildHUD;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Vytvorenie hry
            Game game = new Game();

            // Získanie hernej mapy
            PlayingArea map = game.getPlayingArea();

            // Vytvorenie a umiestnenie WatchTower na pozíciu (50, 50)
            WatchTower tower = new WatchTower(map, 50, 50);
            boolean placed = map.placeBuilding(tower);
            if (placed) {
                System.out.println("WatchTower successfully placed at (50, 50)");
            } else {
                System.out.println("Failed to place WatchTower at (50, 50)");
            }

            // Obnovíme zobrazenie - repaint game panel, aby sa zobrazil nový objekt
            game.getBuildHUD().repaint();

            // Nastavenie akcií tlačidiel v HUD
            BuildHUD buildHUD = game.getBuildHUD();

            buildHUD.getShopButton().addActionListener(e -> {
                Shop shop = new Shop(map, 1000);
                shop.setVisible(true);
            });

            buildHUD.getStartWaveButton().addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Wave started!");
                game.switchHUD("wave");
            });
        });
    }
}
