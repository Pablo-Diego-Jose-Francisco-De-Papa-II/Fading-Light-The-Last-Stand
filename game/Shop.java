package game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
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

        this.addBuyButton("Watch Tower", 100);
        this.addBuyButton("Ballista", 150);
        this.addBuyButton("Mortar", 200);
        this.addBuyButton("Sniper Tower", 250);
        this.addBuyButton("Flamethrower", 300);
        this.addBuyButton("Hellstorm Turret", 400);
        this.addBuyButton("Barricade", 5);
    }

    private void addBuyButton(String name, int cost) {
        this.buildingCosts.put(name, cost); // store cost in map

        JButton button = new JButton(name + " - " + cost);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.addActionListener((ActionEvent e) -> {
            if (this.scrap >= cost) {
                this.selectedBuilding = name;
                JOptionPane.showMessageDialog(this, "Click on the map to place your " + name);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough scrap.");
            }
        });
        add(button);
    }

    public int getCostForBuilding(String name) {
        return this.buildingCosts.getOrDefault(name, Integer.MAX_VALUE);
    }

    public void setMoney(int money) {
        this.scrap = money;
    }

    public int getMoney() {
        return this.scrap;
    }

    public void subtractMoney(int amount) {
        if (this.scrap >= amount) {
            this.setMoney(this.scrap - amount);
        }
    }

    public String getSelectedBuilding() {
        return this.selectedBuilding;
    }

    public void clearSelectedBuilding() {
        this.selectedBuilding = null;
    }
}
