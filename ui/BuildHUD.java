package ui;

import javax.swing.*;

public class BuildHUD extends AbstractHUD {

    private JButton shopButton;
    private JButton startWaveButton;

    public BuildHUD() {
        super();

        this.shopButton = new JButton("Shop");
        this.startWaveButton = new JButton("Start Wave");

        add(this.shopButton);
        add(this.startWaveButton);
    }

    public JButton getShopButton() {
        return this.shopButton;
    }

    public JButton getStartWaveButton() {
        return this.startWaveButton;
    }
}
