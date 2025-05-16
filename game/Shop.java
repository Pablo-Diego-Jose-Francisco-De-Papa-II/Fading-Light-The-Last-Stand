package game;

import buildings.Building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Shop extends JPanel {
    private final PlayingArea map;
    private int scrap;

    public Shop(PlayingArea map, int initialMoney) {
        this.map = map;
        this.scrap = initialMoney;

        setLayout(new GridLayout(0, 1, 5, 5));
        setBounds(500, 100, 250, 300);
        setBackground(new Color(40, 40, 40, 220));

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
            if (scrap >= cost) {
                Building building = Building.createBuilding(name, map, 5, 5);
                if (building != null) {
                    scrap -= cost;
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
        this.scrap = money;
    }

    public int getMoney() {
        return scrap;
    }

    public void addMoney(int amount) {
        setMoney(scrap + amount);
    }

    public void subtractMoney(int amount) {
        if (scrap >= amount) {
            setMoney(scrap - amount);
        }
    }
}
