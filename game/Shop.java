package game;

import buildings.Building;
import buildings.WatchTower;
import buildings.Ballista;
import buildings.Mortar;
import buildings.SniperTower;
import buildings.Flamethrower;
import buildings.HellstormTurret;
import buildings.RocketSilo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop extends JFrame {
    private int playerMoney;
    private PlayingArea map;

    private JButton watchTowerButton;
    private JButton ballistaButton;
    private JButton mortarButton;
    private JButton sniperTowerButton;
    private JButton flamethrowerButton;
    private JButton hellstormTurretButton;
    private JButton rocketSiloButton;
    private JLabel moneyLabel;

    public Shop(int initialMoney, PlayingArea map) {
        this.playerMoney = initialMoney;
        this.map = map;

        setTitle("Shop");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Money label to display available money
        moneyLabel = new JLabel("Money: " + playerMoney);
        add(moneyLabel);

        // Buttons for each building
        watchTowerButton = new JButton("Buy Watch Tower - 100");
        ballistaButton = new JButton("Buy Ballista - 150");
        mortarButton = new JButton("Buy Mortar - 200");
        sniperTowerButton = new JButton("Buy Sniper Tower - 250");
        flamethrowerButton = new JButton("Buy Flamethrower - 300");
        hellstormTurretButton = new JButton("Buy Hellstorm Turret - 400");
        rocketSiloButton = new JButton("Buy Rocket Silo - 500");

        // Add buttons to the frame
        add(watchTowerButton);
        add(ballistaButton);
        add(mortarButton);
        add(sniperTowerButton);
        add(flamethrowerButton);
        add(hellstormTurretButton);
        add(rocketSiloButton);

        // Action listeners for each button
        watchTowerButton.addActionListener(new BuildingButtonListener("Watch Tower"));
        ballistaButton.addActionListener(new BuildingButtonListener("Ballista"));
        mortarButton.addActionListener(new BuildingButtonListener("Mortar"));
        sniperTowerButton.addActionListener(new BuildingButtonListener("Sniper Tower"));
        flamethrowerButton.addActionListener(new BuildingButtonListener("Flamethrower"));
        hellstormTurretButton.addActionListener(new BuildingButtonListener("Hellstorm Turret"));
        rocketSiloButton.addActionListener(new BuildingButtonListener("Rocket Silo"));
    }

    private class BuildingButtonListener implements ActionListener {
        private final String buildingType;

        public BuildingButtonListener(String buildingType) {
            this.buildingType = buildingType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            purchaseBuilding(buildingType);
        }
    }

    private void purchaseBuilding(String buildingType) {
        int buildingCost = getBuildingCost(buildingType);

        if (buildingCost == -1) {
            JOptionPane.showMessageDialog(this, "Invalid building type.");
            return;
        }

        if (playerMoney >= buildingCost) {
            Building building = Building.createBuilding(buildingType, map, 5, 5);
            if (building != null) {
                playerMoney -= buildingCost;
                JOptionPane.showMessageDialog(this, "Purchased " + buildingType);
                updateMoneyLabel();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create the building.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough money.");
        }
    }

    private int getBuildingCost(String buildingType) {
        switch (buildingType) {
            case "Watch Tower":
                return 100;
            case "Ballista":
                return 150;
            case "Mortar":
                return 200;
            case "Sniper Tower":
                return 250;
            case "Flamethrower":
                return 300;
            case "Hellstorm Turret":
                return 400;
            case "Rocket Silo":
                return 500;
            default:
                return -1; // Invalid building
        }
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: " + playerMoney);
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void addMoney(int amount) {
        playerMoney += amount;
        updateMoneyLabel();
    }

    public void subtractMoney(int amount) {
        if (playerMoney >= amount) {
            playerMoney -= amount;
            updateMoneyLabel();
        } else {
            JOptionPane.showMessageDialog(this, "Not enough money.");
        }
    }
}
