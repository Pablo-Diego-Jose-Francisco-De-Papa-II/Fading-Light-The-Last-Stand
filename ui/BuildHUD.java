package ui;

import game.PlayingArea;
import game.Shop;

import javax.swing.*;
import java.awt.*;

public class BuildHUD extends AbstractHUD {

    private final JButton shopButton;
    private final JButton startWaveButton;
    private final Shop shop;

    public BuildHUD(PlayingArea playingArea) {
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
}
