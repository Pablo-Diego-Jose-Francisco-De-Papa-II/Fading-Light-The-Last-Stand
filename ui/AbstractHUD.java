package ui;

import javax.swing.*;

public abstract class AbstractHUD extends JPanel {

    protected final JLabel dayLabel;
    protected final JLabel scrapLabel;
    protected final JLabel enemiesLeftLabel;

    public AbstractHUD() {
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        this.dayLabel = new JLabel("Day: 1");
        this.dayLabel.setBounds(10, 0, 150, 30);
        add(this.dayLabel);

        this.scrapLabel = new JLabel("Scrap: 0");
        this.scrapLabel.setBounds(1200, 0, 150, 30);
        add(this.scrapLabel);

        this.enemiesLeftLabel = new JLabel("Enemies Left: 0");
        this.enemiesLeftLabel.setBounds(10, 20, 200, 30);
        add(this.enemiesLeftLabel);
    }

    public void updateDay(int day) {
        this.dayLabel.setText("Day: " + day);
    }

    public void updateScrap(int scrap) {
        this.scrapLabel.setText("Scrap: " + scrap);
    }

    public void updateEnemiesLeft(int enemiesLeft) {
        this.enemiesLeftLabel.setText("Enemies Left: " + enemiesLeft);
    }
}
