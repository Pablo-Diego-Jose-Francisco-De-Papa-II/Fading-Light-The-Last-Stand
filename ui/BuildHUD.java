package ui;

import javax.swing.*;
import java.awt.*;

public class BuildHUD extends AbstractHUD {

    private final JButton startWaveButton;
    private final JButton openShopButton;

    public BuildHUD() {
        super();

        this.startWaveButton = new JButton("Start Wave");
        this.startWaveButton.setBounds(10, 600, 200, 75);
        this.startWaveButton.setFont(new Font("Arial", Font.BOLD, 30));
        add(this.startWaveButton);

        this.openShopButton = new JButton("Shop");
        this.openShopButton.setBounds(1055, 600, 200, 75);
        this.openShopButton.setFont(new Font("Arial", Font.BOLD, 30));
        add(this.openShopButton);
    }

    public JButton getStartWaveButton() {
        return this.startWaveButton;
    }

    public JButton getOpenShopButton() {
        return this.openShopButton;
    }
}
