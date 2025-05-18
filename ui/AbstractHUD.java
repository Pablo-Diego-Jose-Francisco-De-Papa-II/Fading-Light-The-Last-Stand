package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public abstract class AbstractHUD extends JPanel {

    private final JLabel dayLabel;
    private final JLabel scrapLabel;

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
        this.scrapLabel = new JLabel("0 scraps");
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
