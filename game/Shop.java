package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class Shop extends JPanel {

    private final PlayingArea map;
    private int scrap;
    private String selectedBuilding = null;

    private final Map<String, Integer> buildingCosts = new HashMap<>();

    public Shop(PlayingArea map, int initialMoney) {
        this.map = map;
        this.scrap = initialMoney;

        setLayout(new GridLayout(0, 1, 5, 5));

        addBuyButton("Watch Tower", 100);
        addBuyButton("Ballista", 150);
        addBuyButton("Mortar", 200);
        addBuyButton("Sniper Tower", 250);
        addBuyButton("Flamethrower", 300);
        addBuyButton("Hellstorm Turret", 400);
        addBuyButton("Rocket Silo", 500);
    }

    private void addBuyButton(String name, int cost) {
        buildingCosts.put(name, cost); // store cost in map

        JButton button = new JButton(name + " - " + cost);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.addActionListener((ActionEvent e) -> {
            if (scrap >= cost) {
                selectedBuilding = name;
                JOptionPane.showMessageDialog(this, "Click on the map to place your " + name);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough scrap.");
            }
        });
        add(button);
    }

    public int getCostForBuilding(String name) {
        return buildingCosts.getOrDefault(name, Integer.MAX_VALUE); // fallback to a high number
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

    public String getSelectedBuilding() {
        return selectedBuilding;
    }

    public void clearSelectedBuilding() {
        selectedBuilding = null;
    }
}
