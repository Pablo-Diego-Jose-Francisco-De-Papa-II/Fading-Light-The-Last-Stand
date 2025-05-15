package ui;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractHUD extends JPanel {

    protected final JLabel dayLabel;
    protected final JLabel scrapLabel;

    public AbstractHUD() {
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        ImageIcon dayIcon = new ImageIcon("resources/calendar.png");
        ImageIcon scrapIcon = new ImageIcon("resources/scrap_bg.png");

        this.dayLabel = new JLabel("27", dayIcon, JLabel.LEFT);
        this.dayLabel.setBounds(1175, 65, 75, 75);
        this.dayLabel.setForeground(Color.WHITE);
        add(this.dayLabel);

        this.scrapLabel = new JLabel("420", scrapIcon, JLabel.LEFT);
        this.scrapLabel.setBounds(1020, 5, 250, 50);
        this.scrapLabel.setForeground(Color.WHITE);
        add(this.scrapLabel);
    }

    public void updateDay(int day) {
        this.dayLabel.setText(String.valueOf(day));
    }

    public void updateScrap(int scrap) {
        this.scrapLabel.setText(String.valueOf(scrap));
    }
}
