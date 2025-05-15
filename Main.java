import game.Game;
import game.PlayingArea;
import game.Shop;
import ui.BuildHUD;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Create and launch the game
        Game game = new Game();

        // Get BuildHUD and its shop button
        BuildHUD buildHUD = game.getBuildHUD();

        // Add action listener to the Shop button
        buildHUD.getShopButton().addActionListener(e -> {
            // Open shop with some initial money and reference to the playing area
            Shop shop = new Shop(new PlayingArea(), 500); // Example: 500 starting money
            shop.setVisible(true);
        });

        // You can also add action to the Start Wave button here
        buildHUD.getStartWaveButton().addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Wave started!");
            game.switchHUD("wave");  // Switch to wave HUD when wave starts
        });
    }
}
