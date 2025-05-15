package game;

import buildings.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Shop extends JPanel {
    private final PlayingArea map;
    private int playerMoney;

    private final JLabel moneyLabel;

    public Shop(PlayingArea map, int initialMoney) {
        this.map = map;
        this.playerMoney = initialMoney;

        setLayout(new GridLayout(0, 1, 5, 5));
        setBounds(500, 100, 250, 300);
        setBackground(new Color(40, 40, 40, 220)); // polopriesvitné pozadie

        moneyLabel = new JLabel("Scrap: " + playerMoney);
        moneyLabel.setForeground(Color.WHITE);
        add(moneyLabel);

        addBuyButton("Watch Tower", 100);
        addBuyButton("Ballista", 150);
        addBuyButton("Mortar", 200);
        addBuyButton("Sniper Tower", 250);
        addBuyButton("Flamethrower", 300);
        addBuyButton("Hellstorm Turret", 400);
        addBuyButton("Rocket Silo", 500);
    }

    private void addBuyButton(String name, int cost) {
        JButton button = new JButton("Buy " + name + " - " + cost);
        button.addActionListener((ActionEvent e) -> {
            if (playerMoney >= cost) {
                Building building = Building.createBuilding(name, map, 5, 5);
                if (building != null) {
                    playerMoney -= cost;
                    moneyLabel.setText("Scrap: " + playerMoney);
                    JOptionPane.showMessageDialog(this, "Purchased " + name);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to build.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Not enough scrap.");
            }
        });
        add(button);
    }

    public void setMoney(int money) {
        this.playerMoney = money;
        moneyLabel.setText("Scrap: " + money);
    }

    public int getMoney() {
        return playerMoney;
    }

    public void addMoney(int amount) {
        setMoney(playerMoney + amount);
    }

    public void subtractMoney(int amount) {
        if (playerMoney >= amount) {
            setMoney(playerMoney - amount);
        }
    }
}
