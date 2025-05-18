package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

/**
 * Abstraktná trieda AbstractHUD predstavuje základný používateľský panel (HUD)
 * zobrazujúci aktuálny deň a množstvo dostupného šrotu počas hry.
 *
 * Slúži ako základ pre konkrétne HUD implementácie.
 */
public abstract class AbstractHUD extends JPanel {

    private final JLabel dayLabel;
    private final JLabel scrapLabel;

    /**
     * Konštruktor nastavuje rozloženie HUD panela a inicializuje základné vizuálne komponenty.
     * Zobrazí ikonu kalendára s číslom dňa a pozadie pre ikonu scrapu so zodpovedajúcim textom.
     */
    public AbstractHUD() {
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        ImageIcon dayIcon = null;

        try {
            dayIcon = new ImageIcon("resources/calendar.png");
        } catch (Exception e) {
            System.err.println("Nepodarilo sa načítať ikony: " + e.getMessage());
        }

        // Vytvorenie labelu pre šrot
        this.scrapLabel = new JLabel("500 scraps");
        this.scrapLabel.setBounds(1050, 10, 200, 50);
        this.scrapLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(this.scrapLabel);

        JLabel scrapIconLabel = new JLabel(new ImageIcon("resources/scrap_bg.png"));
        scrapIconLabel.setBounds(1020, 5, 250, 50);
        add(scrapIconLabel);

        // Vytvorenie labelu pre deň
        this.dayLabel = new JLabel("1", dayIcon, JLabel.CENTER);
        this.dayLabel.setBounds(1175, 65, 75, 75);
        this.dayLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.dayLabel.setVerticalTextPosition(JLabel.CENTER);
        this.dayLabel.setFont(new Font("Arial", Font.BOLD, 25));
        add(this.dayLabel);
    }

    /**
     * Aktualizuje zobrazenie dňa na HUD paneli.
     *
     * @param day nový deň, ktorý sa má zobraziť
     */
    public void updateDay(int day) {
        this.dayLabel.setText(String.valueOf(day));
    }

    /**
     * Aktualizuje zobrazenie množstva scrapu na HUD paneli.
     *
     * @param scrap nové množstvo scrapu
     */
    public void updateScrap(int scrap) {
        this.scrapLabel.setText(scrap + " scraps");
    }
}
