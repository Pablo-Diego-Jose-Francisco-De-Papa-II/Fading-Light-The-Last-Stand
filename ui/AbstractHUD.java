package ui;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractHUD extends JPanel {

    protected final JLabel dayLabel;
    protected final JLabel scrapLabel;

    public AbstractHUD() {
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        // Načítanie ikon bezpečným spôsobom
        ImageIcon dayIcon = null;

        try {
            dayIcon = new ImageIcon("resources/calendar.png");
        } catch (Exception e) {
            System.err.println("Nepodarilo sa načítať ikony: " + e.getMessage());
        }

        // Vytvorenie labelu pre šrot
        this.scrapLabel = new JLabel("0 scraps");
        this.scrapLabel.setBounds(1075, 10, 100, 50); // pozícia textu vedľa ikony
        this.scrapLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(this.scrapLabel);

        JLabel scrapIconLabel = new JLabel(new ImageIcon("resources/scrap_bg.png"));
        scrapIconLabel.setBounds(1020, 5, 250, 50);  // pozícia a veľkosť ikony
        add(scrapIconLabel);

        // Vytvorenie labelu pre deň
        this.dayLabel = new JLabel("69", dayIcon, JLabel.CENTER);
        this.dayLabel.setBounds(1175, 65, 75, 75);
        this.dayLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.dayLabel.setVerticalTextPosition(JLabel.CENTER);   // text v strede ikony
        this.dayLabel.setFont(new Font("Arial", Font.BOLD, 25));
        add(this.dayLabel);
    }

    public void updateDay(int day) {
        this.dayLabel.setText(String.valueOf(day));
    }

    public void updateScrap(int scrap) {
        this.scrapLabel.setText(scrap + " scraps");
    }
}
