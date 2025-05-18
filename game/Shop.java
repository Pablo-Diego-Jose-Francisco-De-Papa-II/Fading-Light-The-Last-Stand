package game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Trieda Shop predstavuje obchod, v ktorom si hráč môže vybrať a kúpiť budovy pomocou suroviny zvané "scrap".
 * Každá budova má svoju cenu a je možné ju umiestniť kliknutím na mapu po zakúpení.
 */
public class Shop extends JPanel {

    private final PlayingArea map;
    private int scrap;
    private String selectedBuilding = null;

    private final Map<String, Integer> buildingCosts = new HashMap<>();

    /**
     * Konštruktor triedy Shop.
     *
     * @param map          Referencia na hraciu plochu, kde sa budovy umiestňujú.
     * @param initialMoney Počiatočný počet "scrapu", ktorým hráč disponuje.
     */
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

    /**
     * Pridá tlačidlo do obchodu pre nákup konkrétnej budovy.
     *
     * @param name Názov budovy.
     * @param cost Cena budovy v "scrape".
     */
    private void addBuyButton(String name, int cost) {
        this.buildingCosts.put(name, cost);

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

    /**
     * Získa cenu určenej budovy.
     *
     * @param name Názov budovy.
     * @return Cena budovy, alebo Integer.MAX_VALUE, ak budova nie je známa.
     */
    public int getCostForBuilding(String name) {
        return this.buildingCosts.getOrDefault(name, Integer.MAX_VALUE);
    }

    /**
     * Nastaví aktuálny počet "scrapu".
     *
     * @param scrap Nová hodnota "scrapu".
     */
    public void setScrap(int scrap) {
        this.scrap = scrap;
    }

    /**
     * Vráti aktuálny počet "scrapu".
     *
     * @return Aktuálny počet "scrapu".
     */
    public int getScrap() {
        return this.scrap;
    }

    /**
     * Odpočíta dané množstvo "scrapu", ak je dostatok prostriedkov.
     *
     * @param amount Množstvo na odpočítanie.
     */
    public void subtractScrap(int amount) {
        if (this.scrap >= amount) {
            this.setScrap(this.scrap - amount);
        }
    }

    /**
     * Získa názov aktuálne zvolenej budovy.
     *
     * @return Názov vybranej budovy alebo {@code null}, ak žiadna nie je vybraná.
     */
    public String getSelectedBuilding() {
        return this.selectedBuilding;
    }


    /**
     * Vymaže výber aktuálnej budovy.
     */
    public void clearSelectedBuilding() {
        this.selectedBuilding = null;
    }

}
