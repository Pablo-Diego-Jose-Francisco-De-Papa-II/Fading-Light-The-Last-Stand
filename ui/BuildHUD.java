package ui;

import buildings.Building;
import game.Game;
import game.PlayingArea;
import game.Shop;

import javax.swing.*;
import java.awt.*;

public class BuildHUD extends AbstractHUD {

    private final JButton shopButton;
    private final JButton startWaveButton;
    private final Shop shop;

    private final JButton upgradeButton;
    private final JButton removeButton;
    private Building selectedBuilding;


    public BuildHUD(PlayingArea playingArea, Game game) {
        super();

        // Shop button
        this.shopButton = new JButton("SHOP");
        this.shopButton.setBounds(10, 660, 150, 50);
        this.shopButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.shopButton);

        // Start Wave button
        this.startWaveButton = new JButton("DEFEND");
        this.startWaveButton.setBounds(1120, 660, 150, 50);
        this.startWaveButton.setFont(new Font("Arial", Font.BOLD, 27));
        add(this.startWaveButton);

        // Shop panel (hidden initially)
        this.shop = new Shop(playingArea, 500);
        this.shop.setVisible(false);
        this.shop.setBounds(10, 400, 150, 250);
        add(this.shop);

        // Button action: toggle shop visibility
        shopButton.addActionListener(e -> shop.setVisible(!shop.isVisible()));

        // Upgrade Button
        this.upgradeButton = new JButton("Upgrade");
        this.upgradeButton.setBounds(200, 600, 120, 40); // dočasná pozícia
        this.upgradeButton.setVisible(false);
        add(this.upgradeButton);

        // Remove Button
        this.removeButton = new JButton("Remove");
        this.removeButton.setBounds(330, 600, 120, 40); // dočasná pozícia
        this.removeButton.setVisible(false);
        add(this.removeButton);

        // Action listeners (implementuj podľa potreby)
        upgradeButton.addActionListener(e -> {
            if (selectedBuilding != null) {
                selectedBuilding.upgrade(); // alebo tvoja metóda
                repaint();
            }
        });

        removeButton.addActionListener(e -> {
            if (selectedBuilding != null) {
                selectedBuilding.removeYourself();
                selectedBuilding = null;
                hideUpgradeRemoveButtons();
                repaint();
            }
        });
    }

    // Getters
    public JButton getShopButton() {
        return this.shopButton;
    }

    public JButton getStartWaveButton() {
        return this.startWaveButton;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void showUpgradeRemoveButtons(Building b, int screenX, int screenY) {
        this.selectedBuilding = b;
        this.upgradeButton.setBounds(screenX - 125, screenY + 20, 120, 40);
        this.removeButton.setBounds(screenX + 5, screenY + 20, 120, 40);
        this.upgradeButton.setVisible(true);
        this.removeButton.setVisible(true);
    }

    public void hideUpgradeRemoveButtons() {
        this.upgradeButton.setVisible(false);
        this.removeButton.setVisible(false);
        this.selectedBuilding = null;
    }

}
